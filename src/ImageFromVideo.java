import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.xuggler.Global;

public class ImageFromVideo extends MediaListenerAdapter {


    public static final double SECONDS_BETWEEN_FRAMES = 0.2;

    private static String inputFile;
    private static String outputFilePrefix;
    private static String setProcess;

    private static ArrayList<Integer> allSec;

    private int mVideoStreamIndex = -1;

    private static ImageFromVideo imageFromVideo;

    private long mLastPtsWrite = Global.NO_PTS;
    public static final long MICRO_SECONDS_BETWEEN_FRAMES = (long)(Global.DEFAULT_PTS_PER_SECOND * SECONDS_BETWEEN_FRAMES);

    public static ImageFromVideo getInstance(){
        if(imageFromVideo == null){
            imageFromVideo = new ImageFromVideo();
        }

        return imageFromVideo;
    }

    public void nextVideo(String set, String input, String output) {
        this.inputFile = input;
        this.outputFilePrefix = output;
        this.setProcess = set;
        allSec = new ArrayList<>();
        mainFunction();
    }

    public void mainFunction() {
        IMediaReader mediaReader = ToolFactory.makeReader(inputFile);
        mediaReader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
        mediaReader.addListener(new ImageSnapListener(mLastPtsWrite, mVideoStreamIndex));
        while (mediaReader.readPacket() == null);
    }

    public ArrayList<Integer> getAllSec() {
        return allSec;
    }


    private static class ImageSnapListener extends MediaListenerAdapter{
        long mLastPtsWrite;
        int mVideoStreamIndex;

        ImageSnapListener(long index, int num){
            this.mLastPtsWrite = index;
            this.mVideoStreamIndex = num;
        }

        public void onVideoPicture(IVideoPictureEvent event){
            if(event.getStreamIndex() != mVideoStreamIndex){
                if(mVideoStreamIndex == -1){
                    mVideoStreamIndex = event.getStreamIndex();
                }else{
                    return;
                }
            }

            if(mLastPtsWrite == Global.NO_PTS){
                mLastPtsWrite = event.getTimeStamp() - MICRO_SECONDS_BETWEEN_FRAMES;
            }

            if(event.getTimeStamp() - mLastPtsWrite >= MICRO_SECONDS_BETWEEN_FRAMES){
                double seconds = ((double) event.getTimeStamp()) / Global.DEFAULT_PTS_PER_SECOND;
                String outputFilename = dumpImageToFile(event.getImage(), seconds);
                new GreenScreenProcess(outputFilename);
                System.out.printf("Elapsed time of %6.3f seconds wrote: %s \n", seconds, outputFilename);

                mLastPtsWrite += MICRO_SECONDS_BETWEEN_FRAMES;
            }
        }

        private String dumpImageToFile(BufferedImage image, double sec){
            try{
                sec *= 1000;
                String outputFilename = outputFilePrefix + setProcess + (int)sec + ".png";
                ImageIO.write(image, "png", new File(outputFilename));
                allSec.add((int)sec);
                return outputFilename;
            }catch(IOException e){
                e.printStackTrace();
                return null;
            }
        }

    }
}
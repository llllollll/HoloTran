import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class ImagePosition {
    private static int width  = 360;
    private static int height = 244;

    private static String outputPrefix;
    private static String inputLocation;
    private static ArrayList<Integer> upAllSec;
    private static ArrayList<Integer> downAllSec;
    private static ArrayList<Integer> leftAllSec;
    private static ArrayList<Integer> rightAllSec;
    private static ArrayList<String> allPosition;
    private static int maxNumber;

    private static BufferedImage imageBase;

    public ImagePosition(String output, int number, ArrayList up, ArrayList down, ArrayList left, ArrayList right, ArrayList position) {
        this.inputLocation = output;
        this.outputPrefix = output + "holoTran";
        this.maxNumber = number;
        upAllSec = new ArrayList<>(up);
        downAllSec = new ArrayList<>(down);
        leftAllSec = new ArrayList<>(left);
        rightAllSec = new ArrayList<>(right);
        allPosition = new ArrayList<>(position);
        mainProcess();
    }

    public String getLocationProcess() {
        return outputPrefix;
    }

    public void mainProcess() {
        try {
            for (int i = 0; i < maxNumber; i++) {
                imageBase = new BufferedImage(1920,1080,BufferedImage.TYPE_INT_RGB);
                BufferedImage imageUp = ImageIO.read(new File(inputLocation + allPosition.get(0) + upAllSec.get(i) + ".png"));
                BufferedImage imageDown = ImageIO.read(new File(inputLocation + allPosition.get(1) + downAllSec.get(i) + ".png"));
                BufferedImage imageLeft = ImageIO.read(new File(inputLocation + allPosition.get(2) + leftAllSec.get(i) + ".png"));
                BufferedImage imageRight = ImageIO.read(new File(inputLocation + allPosition.get(3) + rightAllSec.get(i) + ".png"));

                setPosition(imageUp, imageDown, imageLeft, imageRight, imageBase);

                ImageIO.write(imageBase, "png", new File(outputPrefix + i + ".png"));
            }
        } catch (IOException e) {

        }
    }


    public void setPosition(BufferedImage imageUp, BufferedImage imageDown, BufferedImage imageLeft, BufferedImage imageRight, BufferedImage imageBase){

        int halfWidth = imageBase.getWidth() / 2;
        int halfHeight = imageBase.getHeight() / 2;

        int xc = halfWidth - (width / 2);
        int y1 = (halfHeight - height) / 2;
        int y2 = halfHeight + (height / 2);

        int yc = halfHeight - (width / 2);
        int x1 = ((halfWidth - height*3/2) / 4) * 3;
        int x2 = halfWidth + ((height*3/2) / 8);

        Graphics2D g = imageBase.createGraphics();

        rotateFunction(imageUp, 0, 1, xc, y1, g);
        rotateFunction(imageDown, 180, 1, xc, y2, g);
        rotateFunction(imageLeft, 3, 0.75, x1, yc, g);
        rotateFunction(imageRight, 90, 1.25, x2, yc, g);
        g.dispose();
    }

    public void rotateFunction(BufferedImage input, int quadrants, double scale, int x, int y, Graphics2D g){
        AffineTransform tx = new AffineTransform();
        if(quadrants == 3) {
            tx = AffineTransform.getQuadrantRotateInstance(quadrants, input.getWidth() / 2, input.getHeight() / 2);
        } else if (quadrants == 90) {
            tx.translate(input.getWidth() / 2, input.getHeight() / 2);
            tx.rotate(Math.PI / 2);
            tx.translate(-input.getHeight() / 2, -input.getWidth() / 2);
        } else {
            tx = AffineTransform.getRotateInstance(Math.toRadians (quadrants), input.getWidth() / 2, input.getHeight() / 2);
        }

        tx.scale(scale, scale);

        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);


        if(quadrants == 3 || quadrants == 90) {
            g.drawImage(op.filter(input, null), x, y, height*3/2, width, null);
        } else {
            g.drawImage(op.filter(input, null), x, y, width, height, null);
        }
    }

}

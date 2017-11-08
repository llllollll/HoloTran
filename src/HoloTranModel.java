import java.util.ArrayList;

public class HoloTranModel {
    private ArrayList<String> locationFile = new ArrayList<>();
    private ArrayList<Integer> upSec;
    private ArrayList<Integer> downSec;
    private ArrayList<Integer> leftSec;
    private ArrayList<Integer> rightSec;
    private String position;
    private ArrayList<String> setPosition;
    private int min;
    public boolean status = false;

    public void convertVideoHoloTran() {

        min = 0;
        position = "up";
        setPosition = new ArrayList<>();
        String up = locationFile.get(0);
        String down = locationFile.get(1);
        String left = locationFile.get(2);
        String right = locationFile.get(3);

        ImageFromVideo tempVideo = ImageFromVideo.getInstance();
        status = true;
        tempVideo.nextVideo(position, locationFile.get(0), locationFile.get(4));
        upSec = new ArrayList<>(tempVideo.getAllSec());
        setPosition.add(position);

        if(!down.equals(up)) {
            position = "down";
            tempVideo.nextVideo(position, locationFile.get(1), locationFile.get(4));
            downSec = new ArrayList<>(tempVideo.getAllSec());
        } else {
            position = "up";
            downSec = new ArrayList<>(upSec);
        }
        setPosition.add(position);

        if(!left.equals(up) && !left.equals(down)) {
            position = "left";
            tempVideo.nextVideo(position, locationFile.get(2), locationFile.get(4));
            leftSec = new ArrayList<>(tempVideo.getAllSec());
        } else if(left.equals(up)) {
            position = "up";
            leftSec = new ArrayList<>(upSec);
        } else if(left.equals(down)) {
            position = "down";
            leftSec = new ArrayList<>(downSec);
        }
        setPosition.add(position);

        if(!right.equals(up) && !right.equals(down) && !right.equals(left)) {
            position = "right";
            tempVideo.nextVideo("right", locationFile.get(3), locationFile.get(4));
            rightSec = new ArrayList<>(tempVideo.getAllSec());
        } else if(right.equals(up)) {
            position = "up";
            rightSec = new ArrayList<>(upSec);
        } else if(right.equals(down)) {
            position = "down";
            rightSec = new ArrayList<>(downSec);
        } else if(right.equals(left)) {
            position = "left";
            rightSec = new ArrayList<>(leftSec);
        }
        setPosition.add(position);

        min = minSize();
        ImagePosition thePosition = new ImagePosition(locationFile.get(4), min, upSec, downSec, leftSec, rightSec,
                setPosition);
        new VideoFromImage("HoloTran", thePosition.getLocationProcess(), locationFile.get(4), min);
        status = false;
    }

    public int minSize() {
        int result = upSec.size();
        if(result > downSec.size()){
            result = downSec.size();
        }
        if(result > leftSec.size()) {
            result = leftSec.size();
        }
        if (result > rightSec.size()) {
            result = rightSec.size();
        }
        return result;
    }

    public void addPath(ArrayList<String> input) {
        locationFile = new ArrayList<>(input);
    }

    public void addStatus(boolean input) {
        this.status = input;
    }

    public boolean getStatus() {
        return this.status;
    }
}
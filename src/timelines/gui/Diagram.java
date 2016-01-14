package timelines.gui;

import com.sun.istack.internal.NotNull;
import timelines.api.APIImageMetadata;
import timelines.utils.TimeUtils;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by Tobi on 05.12.2015.
 */
public class Diagram {
    private BufferedImage bufferedImage;
    private APIImageMetadata metadata;
    private boolean empty = false;

    public Diagram(BufferedImage bufferedImage, APIImageMetadata metadata){
        this.bufferedImage = bufferedImage;
        this.metadata = metadata;
    }
    public Diagram(BufferedImage bufferedImage, Date startDate, Date endDate, int zoomLevel){
        this.bufferedImage = bufferedImage;
        this.metadata = new APIImageMetadata(startDate, endDate, zoomLevel);
    }

    public Diagram(ImageInputStream imageInputStream) throws IOException{
        this.metadata = new APIImageMetadata(imageInputStream);
        this.bufferedImage = ImageIO.read(imageInputStream);
    }

    public Diagram(byte[] bytes) throws IOException{
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ImageInputStream iis = ImageIO.createImageInputStream(bais);
        this.metadata = new APIImageMetadata(iis);

        bais = new ByteArrayInputStream(bytes);
        this.bufferedImage = ImageIO.read(bais);
    }

    public Diagram(String url){
        this.empty = true;
        Date date;
        try {
            date = TimeUtils.fromString(url.substring(url.lastIndexOf("=")),"yyyy-MM-dd:HH:mm:ss");
        }catch (ParseException e){
            date = null;
            e.printStackTrace();
        }
        this.metadata = new APIImageMetadata(date,null,0);
        this.bufferedImage = null;
    }

    @NotNull
    public BufferedImage getBufferedImage(){
        return this.bufferedImage;
    }
    @NotNull
    public APIImageMetadata getAPIImageMetadata(){
        return this.metadata;
    }
    @NotNull
    public Date getStartDate(){
        return this.metadata.getDateFrom();
    }
    @NotNull
    public Date getEndDate(){
        return this.metadata.getDateTo();
    }
    public int getZoomLevel(){
        return this.metadata.getZoomLevel();
    }
    public boolean isEmpty(){
        return this.empty;
    }
}

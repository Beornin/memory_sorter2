package obj;

import com.drew.metadata.Metadata;

import java.io.File;
import java.io.Serializable;

public class Memory implements Serializable
{
    private static final long serialVersionUID = 6990332356547600903L;
    private String path;
    private String name;
    private int[] firstRgb;
    private int width;
    private int height;
    private File file;
    private long size;
    private boolean picture;
    private boolean video;
    private boolean matched;

    private boolean imported;//use to know if this is the new one that is bad or not

    //RAW types
    private transient Metadata metadata;
    private boolean metaDataLoaded;
    private String date;

    public boolean isImported()
    {
        return imported;
    }

    public void setImported(final boolean imported)
    {
        this.imported = imported;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(final String path)
    {
        this.path = path;
    }

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public int[] getFirstRgb()
    {
        return firstRgb;
    }

    public void setFirstRgb(final int[] firstRgb)
    {
        this.firstRgb = firstRgb;
    }

    public File getFile()
    {
        return file;
    }

    public void setFile(final File file)
    {
        this.file = file;
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(final int width)
    {
        this.width = width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(final int height)
    {
        this.height = height;
    }

    public long getSize()
    {
        return size;
    }

    public void setSize(final long size)
    {
        this.size = size;
    }

    public boolean isPicture()
    {
        return picture;
    }

    public void setPicture(final boolean picture)
    {
        this.picture = picture;
    }

    public boolean isVideo()
    {
        return video;
    }

    public void setVideo(final boolean video)
    {
        this.video = video;
    }

    public boolean isMatched()
    {
        return matched;
    }

    public void setMatched(final boolean matched)
    {
        this.matched = matched;
    }

    public Metadata getMetadata()
    {
        return metadata;
    }

    public void setMetadata(final Metadata metadata)
    {
        this.metadata = metadata;
    }

    public boolean isMetaDataLoaded()
    {
        return metaDataLoaded;
    }

    public void setMetaDataLoaded(final boolean metaDataLoaded)
    {
        this.metaDataLoaded = metaDataLoaded;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(final String date)
    {
        this.date = date;
    }
}
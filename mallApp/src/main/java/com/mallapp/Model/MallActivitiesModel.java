package com.mallapp.Model;

import java.io.Serializable;

/**
 * Created by Sharjeel Haider on 11/18/2015.
 */
public class MallActivitiesModel implements Serializable{
    private String EntityLogo;

    private String ActivityTextTitle;

    private String ImageURL;

    private BannerImagesModel[] BannerImages;

    private String MallName;

    private String DetailText;

    private String ActivityId;

    private String ActivityTextName;

    private String EntityName;

    private String MallPlaceId;

    private String TotalRecords;

    private String BriefText;

    private String EndDate;

    private ActivityCategoriesModel[] ActivityCategories;

    private String StartDate;

    private String RowNumber;

    private String EntityType;

    private String EndTime;

    private String PlaceName;

    private String StartTime;

    private String ActivityName;

    private String EntityId;

    private boolean isFav;

    public String getEntityLogo ()
    {
        return EntityLogo;
    }

    public void setEntityLogo (String EntityLogo)
    {
        this.EntityLogo = EntityLogo;
    }

    public String getActivityTextTitle ()
    {
        return ActivityTextTitle;
    }

    public void setActivityTextTitle (String ActivityTextTitle)
    {
        this.ActivityTextTitle = ActivityTextTitle;
    }

    public String getImageURL ()
    {
        return ImageURL;
    }

    public void setImageURL (String ImageURL)
    {
        this.ImageURL = ImageURL;
    }

    public BannerImagesModel[] getBannerImages ()
    {
        return BannerImages;
    }

    public void setBannerImages (BannerImagesModel[] BannerImages)
    {
        this.BannerImages = BannerImages;
    }

    public String getMallName ()
    {
        return MallName;
    }

    public void setMallName (String MallName)
    {
        this.MallName = MallName;
    }

    public String getDetailText ()
    {
        return DetailText;
    }

    public void setDetailText (String DetailText)
    {
        this.DetailText = DetailText;
    }

    public String getActivityId ()
    {
        return ActivityId;
    }

    public void setActivityId (String ActivityId)
    {
        this.ActivityId = ActivityId;
    }

    public String getActivityTextName ()
    {
        return ActivityTextName;
    }

    public void setActivityTextName (String ActivityTextName)
    {
        this.ActivityTextName = ActivityTextName;
    }

    public String getEntityName ()
    {
        return EntityName;
    }

    public void setEntityName (String EntityName)
    {
        this.EntityName = EntityName;
    }

    public String getMallPlaceId ()
    {
        return MallPlaceId;
    }

    public void setMallPlaceId (String MallPlaceId)
    {
        this.MallPlaceId = MallPlaceId;
    }

    public String getTotalRecords ()
    {
        return TotalRecords;
    }

    public void setTotalRecords (String TotalRecords)
    {
        this.TotalRecords = TotalRecords;
    }

    public String getBriefText ()
    {
        return BriefText;
    }

    public void setBriefText (String BriefText)
    {
        this.BriefText = BriefText;
    }

    public String getEndDate ()
    {
        return EndDate;
    }

    public void setEndDate (String EndDate)
    {
        this.EndDate = EndDate;
    }

    public ActivityCategoriesModel[] getActivityCategories ()
    {
        return ActivityCategories;
    }

    public void setActivityCategories (ActivityCategoriesModel[] ActivityCategories)
    {
        this.ActivityCategories = ActivityCategories;
    }

    public String getStartDate ()
    {
        return StartDate;
    }

    public void setStartDate (String StartDate)
    {
        this.StartDate = StartDate;
    }

    public String getRowNumber ()
    {
        return RowNumber;
    }

    public void setRowNumber (String RowNumber)
    {
        this.RowNumber = RowNumber;
    }

    public String getEntityType ()
    {
        return EntityType;
    }

    public void setEntityType (String EntityType)
    {
        this.EntityType = EntityType;
    }

    public String getEndTime ()
    {
        return EndTime;
    }

    public void setEndTime (String EndTime)
    {
        this.EndTime = EndTime;
    }

    public String getPlaceName ()
    {
        return PlaceName;
    }

    public void setPlaceName (String PlaceName)
    {
        this.PlaceName = PlaceName;
    }

    public String getStartTime ()
    {
        return StartTime;
    }

    public void setStartTime (String StartTime)
    {
        this.StartTime = StartTime;
    }

    public String getActivityName ()
    {
        return ActivityName;
    }

    public void setActivityName (String ActivityName)
    {
        this.ActivityName = ActivityName;
    }

    public String getEntityId ()
    {
        return EntityId;
    }

    public void setEntityId (String EntityId)
    {
        this.EntityId = EntityId;
    }

    public boolean isFav ()
    {
        return isFav;
    }

    public void setFav (boolean isFav)
    {
        this.isFav = isFav;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [EntityLogo = "+EntityLogo+", ActivityTextTitle = "+ActivityTextTitle+", ImageURL = "+ImageURL+", BannerImages = "+BannerImages+", MallName = "+MallName+", DetailText = "+DetailText+", ActivityId = "+ActivityId+", ActivityTextName = "+ActivityTextName+", EntityName = "+EntityName+", MallPlaceId = "+MallPlaceId+", TotalRecords = "+TotalRecords+", BriefText = "+BriefText+", EndDate = "+EndDate+", ActivityCategories = "+ActivityCategories+", StartDate = "+StartDate+", RowNumber = "+RowNumber+", EntityType = "+EntityType+", EndTime = "+EndTime+", PlaceName = "+PlaceName+", StartTime = "+StartTime+", ActivityName = "+ActivityName+", EntityId = "+EntityId+"]";
    }
}

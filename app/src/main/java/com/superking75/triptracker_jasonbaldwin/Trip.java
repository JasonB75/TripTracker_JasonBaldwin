package com.superking75.triptracker_jasonbaldwin;

import java.util.Date;

public class Trip
{

    String mObjectId, mName, mDescription;
    Date mStartDate, mEndDate;
    boolean shared;

    public boolean isShared()
    { return shared; }

    public void setShared(boolean shared)
    { this.shared = shared; }

    public Date getEndDate()
    { return mEndDate; }

    public void setEndDate(Date endDate)
    { this.mEndDate = endDate; }

    public Date getStartDate()
    { return mStartDate; }

    public void setStartDate(Date startDate)
    { this.mStartDate = startDate; }

    public String getDescription()
    { return mDescription; }

    public void setDescription(String description)
    { this.mDescription = description; }

    public String getName()
    { return mName; }

    public void setName(String name)
    { this.mName = name; }

    public String getObjectId()
    { return mObjectId; }

    public void setObjectId(String objectId)
    { this.mObjectId = objectId; }





}

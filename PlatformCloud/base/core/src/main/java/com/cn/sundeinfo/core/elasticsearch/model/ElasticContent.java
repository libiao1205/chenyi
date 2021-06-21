package com.cn.sundeinfo.core.elasticsearch.model;

public class ElasticContent
{
    private String id;
    private Long ownId;
    private String createTime;
    private String createBy;

    public void setId(String id)
    {
        this.id = id;
    }

    public void setOwnId(Long ownId)
    {
        this.ownId = ownId;
    }

    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }

    public void setCreateBy(String createBy)
    {
        this.createBy = createBy;
    }

    public boolean equals(Object o)
    {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ElasticContent)) {
            return false;
        }
        ElasticContent other = (ElasticContent)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Object this$id = getId();Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) {
            return false;
        }
        Object this$ownId = getOwnId();Object other$ownId = other.getOwnId();
        if (this$ownId == null ? other$ownId != null : !this$ownId.equals(other$ownId)) {
            return false;
        }
        Object this$createTime = getCreateTime();Object other$createTime = other.getCreateTime();
        if (this$createTime == null ? other$createTime != null : !this$createTime.equals(other$createTime)) {
            return false;
        }
        Object this$createBy = getCreateBy();Object other$createBy = other.getCreateBy();return this$createBy == null ? other$createBy == null : this$createBy.equals(other$createBy);
    }

    protected boolean canEqual(Object other)
    {
        return other instanceof ElasticContent;
    }

    public int hashCode()
    {
        int PRIME = 59;int result = 1;Object $id = getId();result = result * 59 + ($id == null ? 43 : $id.hashCode());Object $ownId = getOwnId();result = result * 59 + ($ownId == null ? 43 : $ownId.hashCode());Object $createTime = getCreateTime();result = result * 59 + ($createTime == null ? 43 : $createTime.hashCode());Object $createBy = getCreateBy();result = result * 59 + ($createBy == null ? 43 : $createBy.hashCode());return result;
    }

    public String toString()
    {
        return "ElasticContent(id=" + getId() + ", ownId=" + getOwnId() + ", createTime=" + getCreateTime() + ", createBy=" + getCreateBy() + ")";
    }

    public String getId()
    {
        return this.id;
    }

    public Long getOwnId()
    {
        return this.ownId;
    }

    public String getCreateTime()
    {
        return this.createTime;
    }

    public String getCreateBy()
    {
        return this.createBy;
    }
}

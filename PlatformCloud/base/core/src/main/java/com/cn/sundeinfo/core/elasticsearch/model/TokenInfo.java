package com.cn.sundeinfo.core.elasticsearch.model;

public class TokenInfo
{
    private String token;
    private String type;
    private Integer position;
    private Integer start_offset;
    private Integer end_offset;

    public void setToken(String token)
    {
        this.token = token;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public void setPosition(Integer position)
    {
        this.position = position;
    }

    public void setStart_offset(Integer start_offset)
    {
        this.start_offset = start_offset;
    }

    public void setEnd_offset(Integer end_offset)
    {
        this.end_offset = end_offset;
    }

    public boolean equals(Object o)
    {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TokenInfo)) {
            return false;
        }
        TokenInfo other = (TokenInfo)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Object this$token = getToken();Object other$token = other.getToken();
        if (this$token == null ? other$token != null : !this$token.equals(other$token)) {
            return false;
        }
        Object this$type = getType();Object other$type = other.getType();
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) {
            return false;
        }
        Object this$position = getPosition();Object other$position = other.getPosition();
        if (this$position == null ? other$position != null : !this$position.equals(other$position)) {
            return false;
        }
        Object this$start_offset = getStart_offset();Object other$start_offset = other.getStart_offset();
        if (this$start_offset == null ? other$start_offset != null : !this$start_offset.equals(other$start_offset)) {
            return false;
        }
        Object this$end_offset = getEnd_offset();Object other$end_offset = other.getEnd_offset();return this$end_offset == null ? other$end_offset == null : this$end_offset.equals(other$end_offset);
    }

    protected boolean canEqual(Object other)
    {
        return other instanceof TokenInfo;
    }

    public int hashCode()
    {
        int PRIME = 59;int result = 1;Object $token = getToken();result = result * 59 + ($token == null ? 43 : $token.hashCode());Object $type = getType();result = result * 59 + ($type == null ? 43 : $type.hashCode());Object $position = getPosition();result = result * 59 + ($position == null ? 43 : $position.hashCode());Object $start_offset = getStart_offset();result = result * 59 + ($start_offset == null ? 43 : $start_offset.hashCode());Object $end_offset = getEnd_offset();result = result * 59 + ($end_offset == null ? 43 : $end_offset.hashCode());return result;
    }

    public String toString()
    {
        return "TokenInfo(token=" + getToken() + ", type=" + getType() + ", position=" + getPosition() + ", start_offset=" + getStart_offset() + ", end_offset=" + getEnd_offset() + ")";
    }

    public String getToken()
    {
        return this.token;
    }

    public String getType()
    {
        return this.type;
    }

    public Integer getPosition()
    {
        return this.position;
    }

    public Integer getStart_offset()
    {
        return this.start_offset;
    }

    public Integer getEnd_offset()
    {
        return this.end_offset;
    }
}

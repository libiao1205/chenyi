package com.cn.sundeinfo.core.elasticsearch.model;

public class ElasticBaseEntity<T>
{
    private String id;
    private T body;

    public void setId(String id)
    {
        this.id = id;
    }

    public void setBody(T body)
    {
        this.body = body;
    }

    public boolean equals(Object o)
    {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ElasticBaseEntity)) {
            return false;
        }
        ElasticBaseEntity<?> other = (ElasticBaseEntity)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Object this$id = getId();Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) {
            return false;
        }
        Object this$body = getBody();Object other$body = other.getBody();return this$body == null ? other$body == null : this$body.equals(other$body);
    }

    protected boolean canEqual(Object other)
    {
        return other instanceof ElasticBaseEntity;
    }

    public int hashCode()
    {
        int PRIME = 59;int result = 1;Object $id = getId();result = result * 59 + ($id == null ? 43 : $id.hashCode());Object $body = getBody();result = result * 59 + ($body == null ? 43 : $body.hashCode());return result;
    }

    public String toString()
    {
        return "ElasticBaseEntity(id=" + getId() + ", body=" + getBody() + ")";
    }

    public String getId()
    {
        return this.id;
    }

    public T getBody()
    {
        return (T)this.body;
    }
}

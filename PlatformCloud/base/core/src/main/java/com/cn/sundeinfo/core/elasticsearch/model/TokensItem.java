package com.cn.sundeinfo.core.elasticsearch.model;

import java.util.List;

public class TokensItem
{
    private List<TokenInfo> tokens;

    public String toString()
    {
        return "TokensItem(tokens=" + getTokens() + ")";
    }

    public int hashCode()
    {
        int PRIME = 59;int result = 1;Object $tokens = getTokens();result = result * 59 + ($tokens == null ? 43 : $tokens.hashCode());return result;
    }

    protected boolean canEqual(Object other)
    {
        return other instanceof TokensItem;
    }

    public boolean equals(Object o)
    {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TokensItem)) {
            return false;
        }
        TokensItem other = (TokensItem)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Object this$tokens = getTokens();Object other$tokens = other.getTokens();return this$tokens == null ? other$tokens == null : this$tokens.equals(other$tokens);
    }

    public void setTokens(List<TokenInfo> tokens)
    {
        this.tokens = tokens;
    }

    public List<TokenInfo> getTokens()
    {
        return this.tokens;
    }
}


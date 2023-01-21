package org.example;

import Listeners.EventListener;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class Main {
    private final ShardManager shardManager;

    public Main() throws LoginException{
        String token = "MyToken";                                                                   //MyToken - represents the specific token needed that must be inputed by user based on the discord key given
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);       //using shard which allows for the bot to be run on multiple servers
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.watching("Elmo's World"));
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
        builder.enableIntents(GatewayIntent.MESSAGE_CONTENT);
        builder.enableIntents(GatewayIntent.GUILD_PRESENCES);
        builder.setMemberCachePolicy(MemberCachePolicy.ALL);                                        //holds the cache of all members in the server but does so slowly and takes time
        builder.setChunkingFilter(ChunkingFilter.ALL);                                              //allows bot to  cache all users on startup but is intensive especially if used in discord groups with lots of members
        builder.enableCache(CacheFlag.ONLINE_STATUS, CacheFlag.ACTIVITY);                           //stores their online status data
        shardManager = builder.build();

        //registering the listeners
        shardManager.addEventListener(new EventListener());
    }

    //getter so can use later
    public ShardManager getShardManager() {
        return shardManager;
    }

    public static void main(String[] args) {
        try {
            Main bot = new Main();                                                                  //initializing it
        } catch (LoginException e) {                                                                //handle's the exception thrown when the bot token is invalid - using system out to check
            System.out.println("Invalid Bot Token");
        }
    }
}

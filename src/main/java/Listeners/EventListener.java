package Listeners;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

public class EventListener extends ListenerAdapter {                                          //extending to get access to all events provided by JDA API

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        User user = event.getUser();
        String emoji = event.getReaction().getEmoji().getAsReactionCode();                     //using object since .getAsReactionCode() expects object
        String channelMention = event.getChannel().getAsMention();

        String message = user.getAsTag() + " reacted to a message with " + emoji + " in the " + channelMention + " channel!";
        event.getGuild().getDefaultChannel().asTextChannel().sendMessage(message).queue();      //queue is a rest action and must be used on discord actions in order for it to execute in order


    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {                                  //method is partly locked by a Gateway Intent which must be defined in main class
        String message = event.getMessage().getContentRaw();                                    //gets the string in its raw content
        if(message.contains("marco")){                                                          //simple method to test functionality of bot
            event.getChannel().sendMessage("polo").queue();
        }

    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        String avatar = event.getUser().getEffectiveAvatarUrl();
        System.out.println(avatar);
    }

    @Override
    public void onUserUpdateOnlineStatus(UserUpdateOnlineStatusEvent event) {
        List<Member> members = event.getGuild().getMembers();                           //method only works if flags are added to the main class and users are cached on startup so gives accurate reading of who is in the server
        int onlineMembers = 0;
        for(Member member : members){                                                   //looping through the list of members
            if(member.getOnlineStatus() == OnlineStatus.ONLINE){
                onlineMembers++;
            }

        }
        User user = event.getUser();
        String message = "Attention! The user " + user.getAsTag() + "** has changed their online status to  " + event.getNewOnlineStatus().getKey() + " ! " + " We now have " + onlineMembers + " users online in this server!";
        event.getGuild().getDefaultChannel().asTextChannel().sendMessage(message).queue();

    }
}


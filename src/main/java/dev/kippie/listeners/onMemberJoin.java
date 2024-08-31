package dev.kippie.listeners;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class onMemberJoin extends ListenerAdapter {
    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        Dotenv dotenv = Dotenv.load();
        HttpPost post = new HttpPost("http://localhost:11434/api/chat");
        JSONObject json = new JSONObject();

        json.put("model", dotenv.get("MODEL"));
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("role", "user");
        jsonObj.put("content", "Context: Someone named " + event.getUser().getName() + " has joined the server named " + event.getGuild().getName() + ". Your name is" + event.getJDA().getSelfUser().getName() + ". Give him/her a warm welcome by sending them a message! Your personality is " + dotenv.get("PERSONALITY") );

        JSONArray ja = new JSONArray();
        ja.put(jsonObj);

        json.put("messages", ja);

        json.put("stream", false);



        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append(json);

        try {
            post.setEntity(new StringEntity(jsonBuilder.toString()));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {
            JSONObject jsonObject = new JSONObject(EntityUtils.toString(response.getEntity()));

            event.getUser().openPrivateChannel().map(channel -> {
                channel.sendMessage(jsonObject.getJSONObject("message").getString("content")).queue();


                return null;
            }).queue();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    }


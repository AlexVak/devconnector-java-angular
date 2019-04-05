package com.alexvak.devconnectorrest;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class DevconnectorRestApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder().bannerMode(Banner.Mode.OFF)
                .sources(DevconnectorRestApplication.class).run(args);
    }

}

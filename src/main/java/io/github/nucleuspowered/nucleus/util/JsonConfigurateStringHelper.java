/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.util;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.gson.GsonConfigurationLoader;
import org.spongepowered.api.text.TextTemplate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Optional;

public class JsonConfigurateStringHelper {
    private final static TypeToken<TextTemplate> textTemplateTypeToken = TypeToken.of(TextTemplate.class);

    public static Optional<ConfigurationNode> getNodeFromJson(String string) {
        try (final StringReader reader = new StringReader(string); final BufferedReader br = new BufferedReader(reader)) {
            GsonConfigurationLoader loader =  GsonConfigurationLoader
                    .builder()
                    .setSource(() -> br)
                    .build();
            return Optional.of(loader.load());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static String getJsonStringFrom(TextTemplate template) {
        try (final StringWriter writer = new StringWriter(); final BufferedWriter bw = new BufferedWriter(writer)) {
            GsonConfigurationLoader loader =  GsonConfigurationLoader
                    .builder()
                    .setSink(() -> bw)
                    .build();
            ConfigurationNode node = loader.createEmptyNode();
            node.setValue(textTemplateTypeToken, template);
            loader.save(node);
            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }
    }
}

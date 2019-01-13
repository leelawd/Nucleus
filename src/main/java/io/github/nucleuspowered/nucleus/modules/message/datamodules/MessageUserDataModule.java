/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.modules.message.datamodules;

import io.github.nucleuspowered.nucleus.dataservices.modular.DataKey;
import io.github.nucleuspowered.nucleus.storage.dataobjects.modules.IUserDataModule;

public class MessageUserDataModule implements IUserDataModule {

    @DataKey("socialspy")
    private boolean socialspy = false;

    @DataKey("msgtoggle")
    private boolean msgToggle = true;

    public boolean isSocialSpy() {
        return this.socialspy;
    }

    public void setSocialSpy(boolean socialSpy) {
        this.socialspy = socialSpy;
    }

    public boolean isMsgToggle() {
        return this.msgToggle;
    }

    public void setMsgToggle(boolean msgToggle) {
        this.msgToggle = msgToggle;
    }

}

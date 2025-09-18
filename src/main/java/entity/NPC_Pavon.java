package entity;

import main.gamePanel;

public class NPC_Pavon extends Entity{
    public NPC_Pavon(gamePanel gp) {
        super(gp);

        direction = "abajo";
        speed = 1;

        getImage();
    }
    public void getImage() {

        up1 = setup("/BlueBoy/boy_up_1");
        up2 = setup("/BlueBoy/boy_up_2");
        down1 = setup("/BlueBoy/boy_down_1");
        down2 = setup("/BlueBoy/boy_down_2");
        left1 = setup("/BlueBoy/boy_left_1");
        left2 = setup("/BlueBoy/boy_left_2");
        right1 = setup("/BlueBoy/boy_right_1");
        right2 = setup("/BlueBoy/boy_right_2");
    }
}

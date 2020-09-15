package entities.inputHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import entities.Player;
import entities.Skill;

public class PlayerInputHandler {

    private Player player;
    private Skill skill;

    public PlayerInputHandler(Player player){
        this.player = player;
    }

    public void handleInput(float dt){
        float horizontal = 0f;

        if(Gdx.input.isKeyPressed(Input.Keys.A) && !player.playerAnimation.isAttacking){
            horizontal -= 1;
            player.playerAnimation.isLookingToRight = false;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D) && !player.playerAnimation.isAttacking){
            horizontal += 1;
            player.playerAnimation.isLookingToRight = true;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.W)  && !player.playerAnimation.isAttacking){
            player.body.setLinearVelocity(new Vector2(0f, 6f));
        }
        player.body.setLinearVelocity(horizontal * 2f, player.body.getLinearVelocity().y);
        handleSkillInput(dt);
    }

    public void handleSkillInput(float dt){
        if(Gdx.input.isKeyPressed(Input.Keys.F) && !player.playerAnimation.isAttacking){
            skill = new Skill(player.world, player, 50, 37);
            player.playerAnimation.isAttacking = true;
        }else if(player.playerAnimation.isAttacking){
            if(player.playerAnimation.skillTimer >= player.playerAnimation.skillPeriod){
                player.world.destroyBody(skill.body);
                player.playerAnimation.isAttacking = false;
                player.playerAnimation.skillTimer = 0f;
            }else{
                if(player.playerAnimation.isLookingToRight){
                    skill.body.setLinearVelocity(50f * dt, 0);
                }else{
                    skill.body.setLinearVelocity(50f * dt * (-1), 0);
                }
                skill.render(player.game.game.batch, dt);
                player.playerAnimation.skillTimer = player.playerAnimation.skillTimer + dt;
            }
        }
    }

}

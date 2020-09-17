package worldContact;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import entities.Enemy;
import entities.Player;
import entities.Skill;

public class WorldContactListener implements ContactListener {


    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        interactiveBetweenSkillAndEnemy(a, b);
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public void interactiveBetweenSkillAndEnemy(Fixture a, Fixture b){
        if(a.getUserData() instanceof Skill || b.getUserData() instanceof Skill){
            Fixture skill = a.getUserData() instanceof Player ? a : b;
            Fixture enemy = skill == a.getUserData() ? b : a;
            if(enemy.getUserData() instanceof Enemy){
                System.out.println("hit");
                Skill skillObject = (Skill) skill.getUserData();
                Enemy enemyObject = (Enemy) enemy.getUserData();
                float distance = skillObject.player.body.getPosition().x - enemyObject.body.getPosition().x;
                if(distance > 0){
                    enemyObject.body.applyForceToCenter(new Vector2(-100f, 50f), true);
                } else{
                    enemyObject.body.applyForceToCenter(new Vector2(100f, 50f), true);
                }
            }
        }
    }

}

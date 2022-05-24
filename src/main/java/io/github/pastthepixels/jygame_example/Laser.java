package io.github.pastthepixels.jygame_example;

import com.github.jygame.object.*;
import com.github.jygame.physics.RigidBody;

import java.awt.Color;

public class Laser {
    Rect mesh = new Rect();

    RigidBody body;

    Game game;

    double velocity = 15; // Pixels/refresh

    public Laser(Mesh2D sender, RigidBody senderBody, Game game) {
        this.game = game;
        mesh.dbg = true;
        mesh.zIndex = -1;
        mesh.fillColor = Color.RED;
        mesh.size.set(6, 32);
        mesh.position.set(sender.position);
        mesh.rotation = sender.rotation;
        // Physics
        body = new RigidBody(mesh) {
            @Override
            public void onBodyEntered(RigidBody body) {
                if(game.entities.indexOf(body) > -1 && body != senderBody) {
                    remove();
                }
            }
        };
        body.disabled = true;
        body.mass = 0;
        game.physics.add(body);
    }

    public void remove() {
        game.physics.remove(body);
        game.root.remove(mesh);
        game.lasers.remove(this);
    }

    public void update() {
        mesh.position.y += velocity * Math.cos(mesh.rotation);
        mesh.position.x -= velocity * Math.sin(mesh.rotation);
        //bounds
        if(mesh.position.y > game.physics.bounds.y || mesh.position.x > game.physics.bounds.x ) remove();
        if(mesh.position.y < 0                     || mesh.position.x < 0                     ) remove();
    }
}
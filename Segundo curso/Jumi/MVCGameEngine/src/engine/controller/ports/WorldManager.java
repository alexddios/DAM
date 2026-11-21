package engine.controller.ports;

import engine.assets.core.AssetCatalog;
import engine.utils.helpers.DoubleVector;
import engine.world.ports.DefEmitterDTO;

public interface WorldManager {

        public void addDecorator(String assetId, double size, double posX, double posY, double angle);

        public void addDynamicBody(String assetId, double size, double posX, double posY,
                        double speedX, double speedY, double accX, double accY,
                        double angle, double angularSpeed, double angularAcc, double thrust);

        public String addPlayer(String assetId, double size, double posX, double posY,
                        double speedX, double speedY, double accX, double accY,
                        double angle, double angularSpeed, double angularAcc, double thrust);

        public void addStaticBody(String assetId, double size, double posX, double posY, double angle);

        public void equipTrail(
                        String playerId, DefEmitterDTO bodyEmitterDef);

        public void equipWeapon(String playerId, DefEmitterDTO bodyEmitterDef, int shootingOffset);

        public DoubleVector getWorldDimension();

        public EngineState getEngineState();

        public void setLocalPlayer(String playerId);

        public void loadAssets(AssetCatalog assets);

}

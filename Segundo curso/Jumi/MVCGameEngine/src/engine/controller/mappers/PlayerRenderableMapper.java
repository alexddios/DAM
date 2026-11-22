package engine.controller.mappers;

import engine.model.bodies.ports.PlayerDTO;
import engine.view.renderables.ports.PlayerRenderDTO;

public class PlayerRenderableMapper {

    public static PlayerRenderDTO fromPlayerDTO(PlayerDTO playerDto) {
        if (playerDto == null) {
            return null;
        }
        if (playerDto.entityId == null) {
            return null;
        }

        PlayerRenderDTO renderableData = new PlayerRenderDTO(
                playerDto.entityId,
                playerDto.playerName,
                playerDto.damage,
                playerDto.energy,
                playerDto.shieldLevel,
                playerDto.temperature,
                playerDto.activeWeapon,
                playerDto.prymaryAmmoStatus,
                playerDto.secondaryAmmoStatus,
                playerDto.minesStatus,
                playerDto.missilesStatus);

        return renderableData;
    }

}

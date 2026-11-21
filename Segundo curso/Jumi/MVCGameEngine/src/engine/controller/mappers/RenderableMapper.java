package engine.controller.mappers;

import java.util.ArrayList;

import engine.model.bodies.ports.BodyData;
import engine.model.physics.ports.PhysicsValuesDTO;
import engine.view.renderables.ports.RenderDTO;

public class RenderableMapper {

    public static RenderDTO fromBodyDTO(BodyData bodyData) {
        PhysicsValuesDTO phyValues = bodyData.getPhysicsValues();

        if (phyValues == null || bodyData.entityId == null) {
            return null;
        }

        RenderDTO renderablesData = new RenderDTO(
                bodyData.entityId,
                phyValues.posX, phyValues.posY,
                phyValues.angle,
                phyValues.size,
                phyValues.timeStamp);

        return renderablesData;
    }

    public static ArrayList<RenderDTO> fromBodyDTO(ArrayList<BodyData> bodyData) {
        ArrayList<RenderDTO> renderableValues = new ArrayList<>();

        for (BodyData bodyDto : bodyData) {
            RenderDTO renderable = RenderableMapper.fromBodyDTO(bodyDto);
            renderableValues.add(renderable);
        }

        return renderableValues;
    }

}
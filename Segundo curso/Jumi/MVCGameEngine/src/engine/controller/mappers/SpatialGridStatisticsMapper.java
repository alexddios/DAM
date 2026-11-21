package engine.controller.mappers;

import engine.utils.spatial.ports.SpatialGridStatisticsDTO;
import engine.view.renderables.ports.SpatialGridStatisticsRenderDTO;

public class SpatialGridStatisticsMapper {

    public static SpatialGridStatisticsRenderDTO fromSpatialGridStatisticsDTO(SpatialGridStatisticsDTO dto) {
        if (dto == null) {
            return null;
        }

        return new SpatialGridStatisticsRenderDTO(
                dto.nonEmptyCells,
                dto.emptyCells,
                dto.avgBucketSizeNonEmpty,
                dto.maxBucketSize,
                dto.estimatedPairChecks,
                dto.cellSize,
                dto.cellsX,
                dto.cellsY,
                dto.maxCellsPerBody);
    }
}

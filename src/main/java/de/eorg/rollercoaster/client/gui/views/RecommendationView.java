package de.eorg.rollercoaster.client.gui.views;

import com.smartgwt.client.widgets.tile.TileGrid;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

public class RecommendationView extends AbstractView {

	public RecommendationView(EView backView) {
		super(true, false, "back", "finish", backView, EView.RECOMMENDATION_VIEW);

		getHeading().setContents("Recommendation");
		getInstructions().setContents(
				"This is the best known solution!");

		TileGrid tileGrid = new TileGrid();
		tileGrid.setTileWidth(194);
		tileGrid.setTileHeight(165);
		tileGrid.setHeight(400);
		tileGrid.setWidth100();
		tileGrid.setCanReorderTiles(false);
		tileGrid.setShowAllRecords(true);
		// tileGrid.setData(ProviderData.getRecords());

		DetailViewerField pictureField = new DetailViewerField("picture");
		pictureField.setType("image");
		// pictureField.setImageURLPrefix("providers/");
		pictureField.setImageWidth(186);
		pictureField.setImageHeight(120);

		DetailViewerField nameField = new DetailViewerField("name");
		DetailViewerField priceField = new DetailViewerField("price");

		tileGrid.setFields(pictureField, nameField, priceField);

		getContent().addMember(tileGrid);

	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub

	}

}

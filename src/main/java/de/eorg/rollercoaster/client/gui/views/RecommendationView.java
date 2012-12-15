package de.eorg.rollercoaster.client.gui.views;

import com.smartgwt.client.widgets.tile.TileGrid;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

public class RecommendationView extends AbstractView {

	public RecommendationView(boolean showBackButton, boolean showNextButton,
			String backLabel, String nextLabel, EView backView, EView nextView) {
		super(showBackButton, showNextButton, backLabel, nextLabel, backView,
				nextView);

		getHeading().setContents("<h1>Recommendation</h1>");
		getInstructions().setContents(
				"<h2>This is the best known solution!</h2>");

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

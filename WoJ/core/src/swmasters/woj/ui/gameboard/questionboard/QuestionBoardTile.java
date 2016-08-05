package swmasters.woj.ui.gameboard.questionboard;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;

import swmasters.woj.model.Question;

public class QuestionBoardTile extends Widget {
	private Question question;
	private boolean showingAnswer = false;
}

package com.example.ryu.promanager;

/**
 * Created by YM on 2017-11-28.
 */
public class board_item {
    private String board_editor;
    private String board_title;
    private String board_text;
    private String board_date;
    private String board_id;

    public board_item(String board_editor, String board_title, String board_text, String board_id, String board_date) {
       this.board_editor = board_editor;
       this.board_text = board_text;
       this.board_title = board_title;
        this.board_date = board_date;
        this.board_id = board_id;
    }

    public String getBoard_title() {
        return board_title;
    }

    public void setBoard_title(String board_title) {
        this.board_title = board_title;
    }

    public String getBoard_text() {
        return board_text;
    }

    public void setBoard_text(String board_text) {
        this.board_text = board_text;
    }

    public String getBoard_editor() {
        return board_editor;
    }

    public void setBoard_editor(String board_editor) {
        this.board_editor = board_editor;
    }
}

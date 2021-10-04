package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.domain.TrelloList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TrelloValidatorTestSuite {
    @Autowired
    private TrelloValidator trelloValidator;

    @Test
    void shouldFilterBoardsNamedTest() {
        //Given
        List<TrelloList> trelloLists = List.of(new TrelloList("1", "name", true));
        TrelloBoard trelloBoardTest = new TrelloBoard("2","test", trelloLists);
        TrelloBoard trelloBoardNoTest = new TrelloBoard("3", "name", trelloLists);
        List<TrelloBoard> trelloBoards = List.of(trelloBoardNoTest, trelloBoardTest);

        //When
        List<TrelloBoard> filteredList = trelloValidator.validateTrelloBoards(trelloBoards);

        //Then
        assertEquals(1, filteredList.size());
    }



}

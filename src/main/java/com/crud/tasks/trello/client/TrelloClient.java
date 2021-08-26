package com.crud.tasks.trello.client;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Component
@RequiredArgsConstructor
public class TrelloClient {

    private final RestTemplate restTemplate;

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;
    @Value("${trello.app.key}")
    private String trelloAppKey;
    @Value("${trello.app.token}")
    private String trelloToken;
    @Value("${trello.app.username}")
    private String username;

    public List<TrelloBoardDto> getTrelloBoards() {
        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(buildGetUrl(), TrelloBoardDto[].class);
        Arrays.stream(boardsResponse)
                .filter(trelloBoardDto -> Objects.nonNull(trelloBoardDto.getId())
                        && Objects.nonNull(trelloBoardDto.getName()))
                .filter(trelloBoardDto -> trelloBoardDto.getName().contains("Kodilla"))
                .forEach(trelloBoardDto -> {
                    System.out.println(trelloBoardDto.getId() + " " + trelloBoardDto.getName());
                    System.out.println("This board contains lists: ");
                    trelloBoardDto.getLists().forEach(trelloList -> {
                        System.out.println(trelloList.getName() + " - " + trelloList.getId() + " - " + trelloList.isClosed());
                    });
                });

        return Optional.ofNullable(boardsResponse)
                .map(Arrays::asList)
                .orElse(Collections.emptyList());
    }

    private URI buildGetUrl() {
        return UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/" + username + "/boards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("fields", "name,id")
                .queryParam("lists", "all")
                .build()
                .encode()
                .toUri();
    }

    public CreatedTrelloCard createNewCard(TrelloCardDto trelloCardDto) {
        return restTemplate.postForObject(buildPostUrl(trelloCardDto), null, CreatedTrelloCard.class);
    }


        URI buildPostUrl(TrelloCardDto trelloCardDto) {
            return UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/cards")
                    .queryParam("key", trelloAppKey)
                    .queryParam("token", trelloToken)
                    .queryParam("name", trelloCardDto.getName())
                    .queryParam("desc", trelloCardDto.getDescription())
                    .queryParam("pos", trelloCardDto.getPos())
                    .queryParam("idList", trelloCardDto.getListId())
                    .build()
                    .encode()
                    .toUri();

        }
}
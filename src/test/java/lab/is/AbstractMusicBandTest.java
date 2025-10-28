package lab.is;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import lab.is.bd.entities.MusicBand;

public abstract class AbstractMusicBandTest extends SpringBootApplicationTest {
    protected void checkEntityExistByIdAndEqualExpectedMusicBandEntity(
        MusicBand musicBand  // TODO переделать в dto
    ) throws Exception {
        final String endpoint = getEndpointGettingEntityById();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .get(endpoint, musicBand.getId());

        List<ResultMatcher> matchers = new ArrayList<>();
        matchers.addAll(List.of(
            status().isOk(),
            content().contentTypeCompatibleWith("application/json"),
            jsonPath("$.id").value(musicBand.getId()),
            jsonPath("$.name").value(musicBand.getName()),
            jsonPath("$.coordinates.id").value(musicBand.getCoordinates().getId()),
            jsonPath("$.coordinates.x").value(musicBand.getCoordinates().getX()),
            jsonPath("$.coordinates.y").value(musicBand.getCoordinates().getY()),
            jsonPath("$.genre").value(musicBand.getGenre().name()),
            jsonPath("$.numberOfParticipants").value(musicBand.getNumberOfParticipants()),
            jsonPath("$.singlesCount").value(musicBand.getSinglesCount()),
            jsonPath("$.description").value(musicBand.getDescription()),
            jsonPath("$.albumsCount").value(musicBand.getAlbumsCount()),
            jsonPath("$.establishmentDate").value(musicBand.getEstablishmentDate().toString())
        ));

        if (musicBand.getBestAlbum() != null) {
            matchers.addAll(List.of(
                jsonPath("$.bestAlbum.id").value(musicBand.getBestAlbum().getId()),
                jsonPath("$.bestAlbum.name").value(musicBand.getBestAlbum().getName()),
                jsonPath("$.bestAlbum.length").value(musicBand.getBestAlbum().getLength())
            ));
        } else {
            matchers.add(jsonPath("$.bestAlbum").doesNotExist());
        }

        if (musicBand.getStudio() != null) {
            matchers.addAll(List.of(
                jsonPath("$.studio.id").value(musicBand.getStudio().getId()),
                jsonPath("$.studio.name").value(musicBand.getStudio().getName()),
                jsonPath("$.studio.address").value(musicBand.getStudio().getAddress())
            ));
        } else {
            matchers.add(
                jsonPath("$.studio").doesNotExist()
            );
        }

        mockMvc.perform(requestBuilder)
            .andExpectAll(
                matchers.toArray(new ResultMatcher[0])
            );
    }
}

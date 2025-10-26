package lab.is.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import lab.is.bd.entities.MusicBand;
import lab.is.bd.entities.MusicGenre;

@Component
public final class MusicBandSpecifications extends MySpecification<MusicBand> {
    public Specification<MusicBand> nameLike(String fieldValue) {
        return fieldStringValueLike(
            MusicBandFieldNames.NAME,
            fieldValue
        );
    }

    public Specification<MusicBand> genreEquals(MusicGenre fieldValue) {
        if (fieldValue == null) return null;
        return fieldValueEquals(
            MusicBandFieldNames.GENRE,
            fieldValue.name()
        );
    }

    public Specification<MusicBand> descriptionLike(String fieldValue) {
        return fieldStringValueLike(
            MusicBandFieldNames.DESCRIPTION,
            fieldValue
        );
    }

    public Specification<MusicBand> bestAlbumNameLike(String fieldValue) {
        return fieldStringValueFromEntityLike(
            MusicBandFieldNames.BEST_ALBUM,
            MusicBandFieldNames.BEST_ALBUM_NAME,
            fieldValue
        );
    }

    public Specification<MusicBand> studioNameLike(String fieldValue) {
        return fieldStringValueFromEntityLike(
            MusicBandFieldNames.STUDIO,
            MusicBandFieldNames.STUDIO_NAME,
            fieldValue
        );
    }

    public Specification<MusicBand> studioAddressLike(String fieldValue) {
        return fieldStringValueFromEntityLike(
            MusicBandFieldNames.STUDIO,
            MusicBandFieldNames.STUDIO_ADDRESS,
            fieldValue
        );
    }
}


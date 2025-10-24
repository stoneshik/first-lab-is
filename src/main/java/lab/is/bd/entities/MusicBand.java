package lab.is.bd.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "music_bands")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class MusicBand {
    @Id
    @EqualsAndHashCode.Include
    @ToString.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "music_band_seq")
    @SequenceGenerator(name = "music_band_seq", sequenceName = "music_band_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @ToString.Include
    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = {})
    @JoinColumn(
        name = "coordinates_id",
        nullable = false,
        foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT)
    )
    private Coordinates coordinates;

    @ToString.Include
    @NotNull
    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @ToString.Include
    @Enumerated(EnumType.STRING)
    @Column(name = "genre")
    private MusicGenre genre;

    @ToString.Include
    @Positive
    @Column(name = "number_of_participants")
    private Long numberOfParticipants;

    @ToString.Include
    @NotNull
    @Positive
    @Column(name = "singles_count", nullable = false)
    private Long singlesCount;

    @ToString.Include
    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {})
    @JoinColumn(
        name = "best_album_id",
        foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT)
    )
    private Album bestAlbum;

    @ToString.Include
    @Positive
    @Column(name = "albums_count", nullable = false)
    private long albumsCount;

    @ToString.Include
    @NotNull
    @Column(name = "establishment_date", nullable = false)
    private LocalDate establishmentDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {})
    @JoinColumn(
        name = "studio_id",
        foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT)
    )
    private Studio studio;

    @PrePersist
    protected void onCreate() {
        this.creationDate = LocalDateTime.now();
    }
}

/*
* все значения по которым есть фильтрация
* name
* genre
* description
* bestAlbum.name
* studio.name
* studio.address
*/

//package ru.yandex.practicum.filmorate;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import ru.yandex.practicum.filmorate.controller.FilmController;
//import ru.yandex.practicum.filmorate.exception.*;
//import ru.yandex.practicum.filmorate.model.Film;
//import ru.yandex.practicum.filmorate.service.FilmService;
//import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class FilmControllerTest {
//    InMemoryFilmStorage inMemoryFilmStorage = new InMemoryFilmStorage();
//    FilmService filmService = new FilmService(inMemoryFilmStorage);
//
//    private FilmController filmController;
//
//    @BeforeEach
//    public void setUp() {
//        filmController = new FilmController(filmService);
//    }
//
//    @Test
//    public void testCreateFilmWithValidData() throws FilmAlreadyExistException, InvalidNameException, InvalidDurationException, InvalidDescriptionException, InvalidReleaseDateException {
//        Film film = new Film();
//        film.setName("Титаник");
//        film.setDuration(180);
//        film.setDescription("Фильм о вымышленном крушении.");
//        film.setReleaseDate(LocalDate.of(1997, 12, 19));
//
//        Film createdFilm = filmController.create(film);
//
//        assertNotNull(createdFilm);
//        assertNotNull(createdFilm.getId());
//        assertEquals("Титаник", createdFilm.getName());
//        assertEquals(180, createdFilm.getDuration());
//        assertEquals("Фильм о вымышленном крушении.", createdFilm.getDescription());
//        assertEquals(LocalDate.of(1997, 12, 19), createdFilm.getReleaseDate());
//    }
//
//    @Test
//    public void testCreateFilmWithEmptyName() {
//        Film film = new Film();
//        film.setName("");
//        film.setDuration(180);
//        film.setDescription("Фильм о вымышленном крушении.");
//        film.setReleaseDate(LocalDate.of(1997, 12, 19));
//
//        assertThrows(ValidationException.class, () -> filmController.create(film));
//    }
//
//    @Test
//    public void testCreateFilmWithInvalidDescription() {
//        Film film = new Film();
//        film.setName("Титаник");
//        film.setDuration(180);
//        film.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus porta risus nec." +
//                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus porta risus nec." +
//                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus porta risus nec.");
//        film.setReleaseDate(LocalDate.of(1997, 12, 19));
//
//        assertThrows(ValidationException.class, () -> filmController.create(film));
//    }
//
//    @Test
//    public void testCreateFilmWithInvalidReleaseDate() {
//        Film film = new Film();
//        film.setName("Титаник");
//        film.setDuration(180);
//        film.setDescription("Фильм о вымышленном крушении.");
//        film.setReleaseDate(LocalDate.of(1800, 1, 1));
//
//        assertThrows(ValidationException.class, () -> filmController.create(film));
//    }
//
//    @Test
//    public void testCreateFilmWithNegativeDuration() {
//        Film film = new Film();
//        film.setName("Титаник");
//        film.setDuration(-120);
//        film.setDescription("Фильм о вымышленном крушении.");
//        film.setReleaseDate(LocalDate.of(1997, 12, 19));
//
//        assertThrows(ValidationException.class, () -> filmController.create(film));
//    }
//
//    @Test
//    public void testGetFilms() {
//        Film film1 = new Film();
//        film1.setName("Титаник");
//        film1.setDuration(180);
//        film1.setDescription("Фильм о вымышленном крушении.");
//        film1.setReleaseDate(LocalDate.of(1997, 12, 19));
//        filmController.create(film1);
//
//        Film film2 = new Film();
//        film2.setName("Аватар");
//        film2.setDuration(162);
//        film2.setDescription("Бывший морской пехотинец получает задание отправиться на луну Пандору и " +
//                "оказывается на разделе между приказами и защитой мира, которым он считает свой дом.");
//        film2.setReleaseDate(LocalDate.of(2009, 12, 18));
//        filmController.create(film2);
//
//        List<Film> films = filmController.getFilms();
//
//        assertEquals(2, films.size());
//        assertTrue(films.contains(film1));
//        assertTrue(films.contains(film2));
//    }
//
//    @Test
//    public void testUpdateFilmWithValidData() throws FilmAlreadyExistException, InvalidNameException, InvalidDurationException, InvalidDescriptionException, InvalidReleaseDateException, NoSuchFilmException {
//        Film film = new Film();
//        film.setName("Титаник");
//        film.setDuration(180);
//        film.setDescription("Фильм о вымышленном крушении.");
//        film.setReleaseDate(LocalDate.of(1997, 12, 19));
//        Film createdFilm = filmController.create(film);
//
//        Film updatedFilm = new Film();
//        updatedFilm.setId(createdFilm.getId());
//        updatedFilm.setName("Титаник (специальное издание)");
//        updatedFilm.setDuration(195);
//        updatedFilm.setDescription("Специальное издание фильма о вымышленном крушении.");
//        updatedFilm.setReleaseDate(LocalDate.of(1997, 12, 19));
//        Film result = filmController.update(updatedFilm);
//
//        assertEquals(createdFilm.getId(), result.getId());
//        assertEquals("Титаник (специальное издание)", result.getName());
//        assertEquals(195, result.getDuration());
//        assertEquals("Специальное издание фильма о вымышленном крушении.", result.getDescription());
//        assertEquals(LocalDate.of(1997, 12, 19), result.getReleaseDate());
//    }
//
//    @Test
//    public void testUpdateNonExistingFilm() {
//        Film film = new Film();
//        film.setId(12345);
//        film.setName("Титаник");
//        film.setDuration(180);
//        film.setDescription("Фильм о вымышленном крушении.");
//        film.setReleaseDate(LocalDate.of(1997, 12, 19));
//
//        assertThrows(NoSuchFilmException.class, () -> filmController.update(film));
//    }
//}

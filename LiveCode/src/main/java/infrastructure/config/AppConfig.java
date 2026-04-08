package infrastructure.config;


import application.usecase.LibraryService;
import data.adapter.BookRepositoryAdapter;
import data.adapter.MemberRepositoryAdapter;
import data.maper.EntityMapper;
import data.repository.InMemoryDatabase;
import presentation.controller.LibraryController;
import presentation.mapper.BookDtoMapper;

// TOGAF: Technology Architecture — DI Container (simulation of Spring @Configuration)
// Here we assemble all dependencies. In Spring Boot, annotations do this automatically.
// @Configuration + @Bean — this is what it simulates
public class AppConfig {

    public LibraryController buildController() {

        // -- Infrastructure / Data Layer ------------------------------------
        InMemoryDatabase db     = InMemoryDatabase.getInstance();
        EntityMapper mapper = new EntityMapper();

        // -- Data Adapters (implement Application Ports) -------------------
        BookRepositoryAdapter bookRepo   = new BookRepositoryAdapter(db, mapper);
        MemberRepositoryAdapter memberRepo = new MemberRepositoryAdapter(db, mapper);

        // -- Application Layer ---------------------------------------------
        // LibraryService knows only about PORTS (interfaces)
        LibraryService service = new LibraryService(bookRepo, memberRepo);

        // -- Presentation Layer --------------------------------------------
        BookDtoMapper dtoMapper = new BookDtoMapper();
        return new LibraryController(service, dtoMapper);
    }
}
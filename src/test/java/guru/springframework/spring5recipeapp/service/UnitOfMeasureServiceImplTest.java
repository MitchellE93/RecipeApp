package guru.springframework.spring5recipeapp.service;

import guru.springframework.spring5recipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.spring5recipeapp.model.UnitOfMeasure;
import guru.springframework.spring5recipeapp.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UnitOfMeasureServiceImplTest {

    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;

    private UnitOfMeasureService unitOfMeasureService;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks( this );
        unitOfMeasureService = new UnitOfMeasureServiceImpl( unitOfMeasureRepository,
                new UnitOfMeasureToUnitOfMeasureCommand() );
    }

    @Test
    void listAllUoms()
    {
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId( 1L );
        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId( 2L );
        UnitOfMeasure uom3 = new UnitOfMeasure();
        uom3.setId( 3L );

        Set<UnitOfMeasure> uoms = new HashSet<>();
        uoms.add( uom );
        uoms.add( uom2 );
        uoms.add( uom3 );

        when( unitOfMeasureRepository.findAll() ).thenReturn( uoms );

        Set<UnitOfMeasureCommand> unitOfMeasureCommands = unitOfMeasureService.listAllUoms();

        assertEquals( 3, unitOfMeasureCommands.size() );
        verify( unitOfMeasureRepository, times( 1 ) ).findAll();
    }
}
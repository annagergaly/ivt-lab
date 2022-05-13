package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore ts1;
  private TorpedoStore ts2;

  @BeforeEach
  public void init(){
    ts1 = mock(TorpedoStore.class);
    ts2 = mock(TorpedoStore.class);
    this.ship = new GT4500(ts1, ts2);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(ts1.fire(1)).thenReturn(true);
    when(ts2.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(ts1, times(1)).fire(1);
    verify(ts2, times(0)).fire(1);
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_Single_SecondAfterFirst(){
    // Arrange
    when(ts1.fire(1)).thenReturn(true);
    when(ts2.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(ts1, times(1)).fire(1);
    verify(ts2, times(1)).fire(1);
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_Single_FirstEmpty(){
    // Arrange
    when(ts1.isEmpty()).thenReturn(true);
    when(ts1.fire(1)).thenReturn(false);
    when(ts2.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(ts1, times(0)).fire(1);
    verify(ts2, times(1)).fire(1);
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_Single_Failure(){
    // Arrange
    when(ts1.isEmpty()).thenReturn(true);
    when(ts2.isEmpty()).thenReturn(true);
    when(ts1.fire(1)).thenReturn(false);
    when(ts2.fire(1)).thenReturn(false);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(ts1, times(0)).fire(1);
    verify(ts2, times(0)).fire(1);
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedo_Single_AfterAll(){
    // Arrange
    when(ts1.isEmpty()).thenReturn(false);
    when(ts2.isEmpty()).thenReturn(false);
    when(ts1.fire(1)).thenReturn(true);
    when(ts2.fire(1)).thenReturn(true);
    // Act
    ship.fireTorpedo(FiringMode.ALL);
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(ts1, times(2)).fire(1);
    verify(ts2, times(1)).fire(1);
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_Single_AfterSingle(){
    // Arrange
    when(ts1.isEmpty()).thenReturn(false);
    when(ts2.isEmpty()).thenReturn(true);
    when(ts1.fire(1)).thenReturn(true);
    when(ts2.fire(1)).thenReturn(false);
    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(ts1, times(2)).fire(1);
    verify(ts2, times(0)).fire(1);
    assertEquals(true, result);
  }


  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(ts1.fire(1)).thenReturn(true);
    when(ts1.isEmpty()).thenReturn(false);
    when(ts2.fire(1)).thenReturn(false);
    when(ts2.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(ts1, times(1)).isEmpty();
    verify(ts2, times(1)).isEmpty();
    verify(ts1, times(1)).fire(1);
    verify(ts2, times(0)).fire(1);
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_All_BothFire(){
    // Arrange
    when(ts1.fire(1)).thenReturn(true);
    when(ts1.isEmpty()).thenReturn(false);
    when(ts2.fire(1)).thenReturn(true);
    when(ts2.isEmpty()).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(ts1, times(1)).isEmpty();
    verify(ts2, times(1)).isEmpty();
    verify(ts1, times(1)).fire(1);
    verify(ts2, times(1)).fire(1);
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_All_Failure(){
    // Arrange
    when(ts1.fire(1)).thenReturn(false);
    when(ts1.isEmpty()).thenReturn(true);
    when(ts2.fire(1)).thenReturn(false);
    when(ts2.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(ts1, times(1)).isEmpty();
    verify(ts2, times(1)).isEmpty();
    verify(ts1, times(0)).fire(1);
    verify(ts2, times(0)).fire(1);
    assertEquals(false, result);
  }

}

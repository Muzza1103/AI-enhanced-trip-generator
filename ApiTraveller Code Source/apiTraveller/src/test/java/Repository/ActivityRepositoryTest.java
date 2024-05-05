package Repository;

import com.l3g1.apitraveller.model.Activity;
import com.l3g1.apitraveller.model.enumeration.ActivityType;
import com.l3g1.apitraveller.model.City;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.l3g1.apitraveller.repository.ActivityRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

//Test methods for ActivityRepository
@ExtendWith(MockitoExtension.class)
public class ActivityRepositoryTest {

    @Mock
    private ActivityRepository  activityRepository;



    @Test
    @DisplayName("testFinAllByCityAndActivityType")
    public void testFindAllByCityAndActivityType() {
        City city = new City();
        ActivityType activityType = ActivityType.ALL;
        List<Activity> mockActivities = Collections.singletonList(new Activity());

        when(activityRepository.findAllByCityAndActivityType(city, activityType)).thenReturn(mockActivities);

        List<Activity> result = activityRepository.findAllByCityAndActivityType(city, activityType);

        assertEquals(mockActivities, result);
    }

    @Test
    @DisplayName("FindAllByCity")
    public void testFindAllByCity() {

        City city = new City();
        List<Activity> mockActivities = Collections.singletonList(new Activity());

        when(activityRepository.findAllByCity(city)).thenReturn(mockActivities);

        List<Activity> result = activityRepository.findAllByCity(city);
        assertEquals(mockActivities, result);
    }

}

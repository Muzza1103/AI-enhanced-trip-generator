package Service;
import com.l3g1.apitraveller.controller.ActivityController;
import com.l3g1.apitraveller.model.Activity;

import com.l3g1.apitraveller.service.ActivityService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;

class ActivityServiceTest {
    ActivityService activityService = Mockito.mock(ActivityService.class);

    ActivityController activityController = new ActivityController();

   Activity activity ;

    Long id = 0L ;
    @Test
    @DisplayName("ActivityService getActivity()")
    public void getActivityTest(){
      Iterable<Activity>activity2 = new ArrayList<>();
      assertEquals(activity2,activityService.getActivity());

    }
    @Test
    @DisplayName("ActivityService getActivity(id)")
    public void getActivityTestbyId(){
        Optional<Activity>activity1 = Optional.empty();
        assertEquals(activity1,activityService.getActivity(id));
    }

    @Test
    @DisplayName("ActivityService deleteActivity(id)")
    public void deleteActivityTestbyId(){
        activityService.deleteActivity(id);
        assertFalse(activityService.getActivity(id).isPresent());
    }
    @Test
    @DisplayName("ActivityService savaActivity(activity)")
    public void saveActivityTest() {
        assertEquals(activity, activityService.saveActivity(activity));
    }



}
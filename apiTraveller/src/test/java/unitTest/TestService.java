package unitTest;

import com.l3g1.apitraveller.controller.ActivityController;
import com.l3g1.apitraveller.model.Activity;
import com.l3g1.apitraveller.service.ActivityService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest()
@Transactional

class TestService {
    Activity activity = new Activity();
    Long id = 1L;
    ActivityService activityService = new ActivityService();

    @Test
    @DisplayName("ActivityService")
    public void AcivityServiceTest(){
      assertEquals(activity, activityService.getActivity());
      assertEquals(activity,activityService.getActivity(id));
      activityService.deleteActivity(id);
      assertNull(activityService.getActivity(id));
      assertEquals(activity,activityService.saveActivity(activity));

    }




}
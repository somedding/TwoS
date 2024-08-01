package test.test_Internet.ScreenTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import test.test_Internet.UsageData.UsageDataEntity;
import java.util.List;

@RestController
@RequestMapping("/api/screen-time")
public class ScreenTimeController {

    @Autowired
    private ScreenTimeService screenTimeService;

    @PostMapping("/classify")
    public void classifyUsageData(@RequestBody List<UsageDataEntity> usageData) {
        screenTimeService.classifyAndSave(usageData);
    }
}

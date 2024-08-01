package test.test_Internet.ScreenTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.test_Internet.UsageData.UsageDataEntity;

import java.time.LocalDateTime;
import java.util.List;


import java.util.HashMap;
import java.util.Map;

@Service
public class ScreenTimeService {
    private final ScreenTimeRepository screenTimeRepository;

    @Autowired
    public ScreenTimeService(ScreenTimeRepository screenTimeRepository) {
        this.screenTimeRepository = screenTimeRepository;
    }

    // 이메일과 카테고리를 기준으로 스크린 타임 데이터를 찾는 메서드
    public ScreenTimeEntity findByEmailAndCategory(String email, String category) {
        return screenTimeRepository.findByEmailAndCategory(email, category);
    }

    // 스크린 타임 엔티티 저장 메서드
    public void save(ScreenTimeEntity screenTimeEntity) {
        // 이메일과 카테고리를 기준으로 기존 엔티티 찾기
        ScreenTimeEntity existingEntity = findByEmailAndCategory(screenTimeEntity.getEmail(), screenTimeEntity.getCategory());

        if (existingEntity != null) {
            // 기존 엔티티가 존재하면 업데이트
            existingEntity.setTotalDuration(existingEntity.getTotalDuration() + screenTimeEntity.getTotalDuration());
            existingEntity.setPercentage((existingEntity.getTotalDuration() / calculateTotalUsageTime(existingEntity.getEmail())) * 100); // 비율 재계산
            existingEntity.setUpdatedAt(LocalDateTime.now());
            screenTimeRepository.save(existingEntity);
        } else {
            // 존재하지 않으면 새로 저장
            screenTimeRepository.save(screenTimeEntity);
        }
    }

    // 전체 사용 시간 계산 (예시, 실제 구현에 따라 다를 수 있음)
    private double calculateTotalUsageTime(String email) {
        double totalUsageTime = 0.0;
        List<ScreenTimeEntity> allData = screenTimeRepository.findByEmail(email);
        for (ScreenTimeEntity entity : allData) {
            totalUsageTime += entity.getTotalDuration();
        }
        return totalUsageTime;
    }

    public void classifyAndSave(List<UsageDataEntity> usageData) {
        Map<String, Double> categoryDurationMap = new HashMap<>();
        double totalDuration = 0.0;

        for (UsageDataEntity data : usageData) {
            String category = classifyDomain(data.getDomain());
            categoryDurationMap.put(category, categoryDurationMap.getOrDefault(category, 0.0) + data.getDuration());
            totalDuration += data.getDuration();
        }

        for (Map.Entry<String, Double> entry : categoryDurationMap.entrySet()) {
            double percentage = (entry.getValue() / totalDuration) * 100;
            ScreenTimeEntity screenTimeEntity = new ScreenTimeEntity();
            screenTimeEntity.setCategory(entry.getKey());
            screenTimeEntity.setTotalDuration(entry.getValue());
            screenTimeEntity.setPercentage(percentage);
            screenTimeEntity.setUpdatedAt(LocalDateTime.now());
            screenTimeEntity.setEmail("example@gmail.com"); // 필요시 이메일 추가

            // 수정된 save 메서드 호출
            save(screenTimeEntity);
        }
    }

    public String classifyDomain(String domain) {
        if (domain.contains("facebook") || domain.contains("instagram") || domain.contains("x.com") ||
                domain.contains("twitter") || domain.contains("threads") || domain.contains("blog") || domain.contains("band.us") ||
                domain.contains("tistory") || domain.contains("blogger") || domain.contains("story.kakao") || domain.contains("velog") ||
                domain.contains("linkedin") || domain.contains("whatsapp") || domain.contains("wechat") || domain.contains("snapchat")) {
            return "Media";
        } else if (domain.contains("netflix") || domain.contains("watcha") || domain.contains("disney") || domain.contains("youtube") || domain.contains("tving") ||
                domain.contains("wavve") || domain.contains("지니티비") || domain.contains("bflix.co.kr") || domain.contains("coupangplay") ||
                domain.contains("collectio.kr") || domain.contains("dorama.kr") || domain.contains("heavenly.tv") || domain.contains("tv.kakao") || domain.contains("kbsplus.kbs.co.k") || domain.contains("moa-play.com") ||
                domain.contains("tv.naver.com") || domain.contains("serieson.naver.com") || domain.contains("motvlnk.uplus.co.kr") || domain.contains("apple-tv-plus") || domain.contains("amcplus.com") ||
                domain.contains("crunchyroll.com") || domain.contains("hulu.com") || domain.contains("www.mlb.com") || domain.contains("max.com") || domain.contains("mgmplus.com") || domain.contains("paramountplus.com") || domain.contains("peacocktv.com") ||
                domain.contains("primevideo.com") || domain.contains("wowpresentsplus.com") || domain.contains("espn.com") || domain.contains("iq.com") || domain.contains("le.com") || domain.contains("v.qq.com") || domain.contains("mgtv.com") || domain.contains("viu.com/ott") ||
                domain.contains("afreecatv") || domain.contains("paramountplus") || domain.contains("youku.tv") || domain.contains("abema.tv") || domain.contains("animestore.docomo.ne.jp") || domain.contains("lemino.docomo.ne.jp") || domain.contains("linetv.tw") ||
                domain.contains("fod.fujitv.co.jp") || domain.contains("paravi.jp") || domain.contains("telasa.co.jp") || domain.contains("tver.jp") || domain.contains("video.unext.jp") || domain.contains("hulu.jp") ||
                domain.contains("rakuten.co.jp") || domain.contains("tiktok.com")) {
            return "Video";
        } else if (domain.contains("google") || domain.contains("naver") || domain.contains("daum") || domain.contains("bing.com") ||
                domain.contains("zum.com") || domain.contains("nate.com") || domain.contains("dreamwiz.com") || domain.contains("korea.com") || domain.contains("duckduckgo.com") ||
                domain.contains("namu.wiki") || domain.contains("aol.com") || domain.contains("annas-archive.org") || domain.contains("yahoo.co.jp") || domain.contains("yahoo.com") ||
                domain.contains("wolframalpha.com") || domain.contains("duckduckgo.com") || domain.contains("brave.com")) {
            return "Search";
        } else {
            return "etc";
        }
    }
}

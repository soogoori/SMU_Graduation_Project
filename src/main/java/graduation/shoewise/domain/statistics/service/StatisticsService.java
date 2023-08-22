package graduation.shoewise.domain.statistics.service;

import graduation.shoewise.domain.review.dto.ReviewStatisticsResponseDto;
import graduation.shoewise.domain.shoes.Shoes;
import graduation.shoewise.domain.shoes.repository.ShoesRepository;
import graduation.shoewise.domain.statistics.dto.PercentDto;
import graduation.shoewise.domain.statistics.dto.StatisticsDto;

import graduation.shoewise.domain.statistics.repository.StatisticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsService {

    public final StatisticsRepository statisticsRepository;
    public final ShoesRepository shoesRepository;

    long totalCount = 0;
    public List<PercentDto.FeelingPercentDto> getFeelingPercent(Long shoesId){
        Shoes shoes = shoesRepository.findById(shoesId).orElseThrow();
        List<PercentDto.FeelingPercentDto> feelingCounts = statisticsRepository.findFeelingCountByShoesId(shoes.getId());

        totalCount = 0;

        for (PercentDto.FeelingPercentDto feelingCount : feelingCounts) {
            totalCount += feelingCount.getCount();
            log.info("feelingCount.getCount() : " + feelingCount.getCount());
        }

        log.info("착화감 통계 내는 중, totalCnt = " + totalCount);

        for (PercentDto.FeelingPercentDto feelingCount : feelingCounts) {
            feelingCount.setCount(feelingCount.getCount()/(double)totalCount*100);
            log.info("feelingCount.getCount() : " + feelingCount.getCount());
            log.info("착화감 통계 내는 중 - Feeling & FeelingCount : " + feelingCount.getFeeling() + " " + feelingCount.getCount());
        }
       return feelingCounts;
    }

    public List<PercentDto.FitPercentDto> getFitPercent(Long shoesId){
        Shoes shoes = shoesRepository.findById(shoesId).orElseThrow();
        List<PercentDto.FitPercentDto> fitCounts = statisticsRepository.findFitCountByShoesId(shoes.getId());

        totalCount = 0;

        for (PercentDto.FitPercentDto fitCount : fitCounts) {
            totalCount += fitCount.getCount();
            log.info("fitCount.getCount() : " + fitCount.getCount());

        }
        log.info("사이즈 잘 맞는지 통계 내는 중, totalCnt = " + totalCount);

        for (PercentDto.FitPercentDto fitCount : fitCounts) {
            fitCount.setCount(fitCount.getCount()/totalCount*100);
            log.info("fitCount.getCount() : " + fitCount.getCount());
            log.info("사이즈 잘 맞는지 통계 내는 중  - FIT & FitCount : " + fitCount.getFit() + " " + fitCount.getCount());
        }
        return fitCounts;
    }

    public List<PercentDto.WidthDto> getWidthPercent(Long shoesId){
        Shoes shoes = shoesRepository.findById(shoesId).orElseThrow();
        List<PercentDto.WidthDto> widthCounts = statisticsRepository.findWidthCountByShoesId(shoes.getId());

        totalCount = 0;

        for (PercentDto.WidthDto widthCount : widthCounts) {
            totalCount += widthCount.getCount();
            log.info("widthCount.getCount() : " + widthCount.getCount());

        }
        log.info("폭 잘 맞는지 통계 내는 중, totalCnt = " + totalCount);

        for (PercentDto.WidthDto widthCount : widthCounts) {
            widthCount.setCount(widthCount.getCount()/totalCount*100);
            log.info("widthCount.getCount() : " + widthCount.getCount());

            log.info("폭 잘 맞는지 통계 내는 중 - FIT & FitCount : " + widthCount.getWidth() + " " + widthCount.getCount());
        }
        return widthCounts;
    }
}

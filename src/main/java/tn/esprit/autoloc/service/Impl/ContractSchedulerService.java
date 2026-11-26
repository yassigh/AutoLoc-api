package tn.esprit.autoloc.service.Impl;

import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.repository.IContratRepository;
import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ContractSchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(ContractSchedulerService.class);

    private final IContratRepository contratRepository;

    public ContractSchedulerService(IContratRepository contratRepository) {
        this.contratRepository = contratRepository;
    }
    @Scheduled(cron = "0 0 7 * * *") // 07:00 daily
    @Transactional
    public void identifyExpiringContracts() {
        LocalDate today = LocalDate.now();
        LocalDate inTwoDays = today.plusDays(2);
        List<Contrat> contracts = contratRepository.findByDateSignatureBetween(today, inTwoDays);
        if (contracts.isEmpty()) {
            logger.info("No contracts expiring within the next 48 hours on {}.", today);
        } else {
            logger.info("Contracts expiring within the next 48 hours ({} to {}):", today, inTwoDays);
            contracts.forEach(contract -> logger.info("Contract ID: {} signed on {}", contract.getIdContrat(), contract.getDateSignature()));
        }
    }
}

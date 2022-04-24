import entity.BloodPressure;
import entity.HealthInfo;
import entity.PatientInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import repository.PatientInfoRepository;
import service.alert.SendAlertService;
import service.medical.MedicalService;
import service.medical.MedicalServiceImpl;
import java.math.BigDecimal;
import java.time.LocalDate;

public class MedicalServiceImplTest {
    @BeforeEach
    public void initTest() {
        System.out.println("Test start");
    }

    @AfterEach
    public void finalizeTest() {
        System.out.println("\nTest complete");
    }

    @Test
    public void checkBloodPressureTest () {

        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById(Mockito.anyString())).thenReturn(new PatientInfo(
                "123","Иван","Петров", LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal(36.65), new BloodPressure(120,80))));


        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        medicalService.checkBloodPressure("123", new BloodPressure(100, 80));

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        Mockito.verify(sendAlertService).send(argumentCaptor.capture());
        Assertions.assertEquals("Warning, patient with id: 123, need help", argumentCaptor.getValue());
    }

    @Test
    public void checkTemperatureTest () {
        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById(Mockito.anyString())).thenReturn(new PatientInfo(
                "123","Иван","Петров", LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal(36.65), new BloodPressure(120,80))));


        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        medicalService.checkTemperature("123", new BigDecimal(35.00));

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        Mockito.verify(sendAlertService).send(argumentCaptor.capture());
        Assertions.assertEquals("Warning, patient with id: 123, need help", argumentCaptor.getValue());
    }

    @Test
    public void checkNormalTemperatureTest () {
        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById(Mockito.anyString())).thenReturn(new PatientInfo(
                "123","Иван","Петров", LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal(36.65), new BloodPressure(120,80))));


        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        medicalService.checkTemperature("123", new BigDecimal(36.65));

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        Mockito.verify(sendAlertService, Mockito.times(0)).send(argumentCaptor.capture());
    }

    @Test
    public void checkNormalBloodPressureTest () {
        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById(Mockito.anyString())).thenReturn(new PatientInfo(
                "123","Иван","Петров", LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal(36.65), new BloodPressure(120,80))));


        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        medicalService.checkBloodPressure("123", new BloodPressure(120, 80));

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        Mockito.verify(sendAlertService, Mockito.times(0)).send(argumentCaptor.capture());
    }
}

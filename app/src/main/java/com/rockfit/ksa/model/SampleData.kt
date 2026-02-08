package com.rockfit.ksa.model

import androidx.compose.ui.graphics.Color
import com.rockfit.ksa.R

object SampleData {
    val athleteSnapshot = AthleteSnapshot(
        name = "Sami Al Harbi",
        sport = "Football - Riyadh Academy",
        recoveryScore = 86,
        readiness = "High readiness",
        injuryRisk = "Low"
    )

    val athleteProfile = AthleteProfile(
        fullName = "Sami Al Harbi",
        birthYear = 2005,
        heightCm = 178,
        weightKg = 70,
        bmi = 22.1,
        bodyFatPercent = 12.8,
        muscleMassKg = 31.2,
        hydrationLiters = 2.4,
        sleepScore = 86
    )

    val vitals = listOf(
        HealthMetric("Heart rate", "58", "bpm", MetricStatus.Stable),
        HealthMetric("Blood pressure", "118/78", "mmHg", MetricStatus.Stable),
        HealthMetric("SpO2", "98", "%", MetricStatus.Stable),
        HealthMetric("Body temp", "36.7", "C", MetricStatus.Stable),
        HealthMetric("Hydration", "2.4", "L", MetricStatus.Warning),
        HealthMetric("Sleep", "7.1", "hrs", MetricStatus.Warning)
    )

    val healthReadings = listOf(
        HealthReading("Resting HR", "58 bpm", "Jan 28, 2026"),
        HealthReading("Sleep", "7.1 hrs", "Jan 28, 2026"),
        HealthReading("Hydration", "2.4 L", "Jan 28, 2026"),
        HealthReading("Body fat", "12.8%", "Jan 25, 2026")
    )

    val injuries = listOf(
        InjuryLog("Left ankle sprain", "Dec 05, 2025", "Recovered", "Jan 02, 2026"),
        InjuryLog("Hamstring tightness", "Jan 18, 2026", "Monitoring", "Jan 31, 2026")
    )

    val medicalReport = MedicalReport(
        summary = "No active injury cases. Cleared for full training.",
        prescription = "Mobility + posterior chain strength. Hydration booster.",
        doctor = "Dr. Noor Al Jaber",
        date = "Jan 12, 2026",
        returnToPlay = ReturnToPlayStatus.Cleared
    )

    val trainingSessions = listOf(
        TrainingSession("Acceleration + agility", load = 78, durationMinutes = 75, distanceKm = 5.2),
        TrainingSession("Strength + mobility", load = 64, durationMinutes = 60, distanceKm = 2.1),
        TrainingSession("Tactical scrimmage", load = 82, durationMinutes = 90, distanceKm = 7.4)
    )

    val skills = listOf(
        SkillScore("Acceleration", 84),
        SkillScore("Agility", 78),
        SkillScore("Tactical IQ", 90),
        SkillScore("Technique", 74),
        SkillScore("Strength", 81)
    )

    val skillTrends = listOf(
        SkillTrend(
            label = "Acceleration",
            currentScore = 84,
            lastMonth = 81,
            lastYear = 72,
            weeklyTrend = listOf(72, 74, 76, 78, 80, 82, 84)
        ),
        SkillTrend(
            label = "Agility",
            currentScore = 78,
            lastMonth = 76,
            lastYear = 69,
            weeklyTrend = listOf(68, 70, 71, 73, 75, 77, 78)
        ),
        SkillTrend(
            label = "Tactical IQ",
            currentScore = 90,
            lastMonth = 88,
            lastYear = 82,
            weeklyTrend = listOf(82, 84, 85, 86, 88, 89, 90)
        ),
        SkillTrend(
            label = "Technique",
            currentScore = 74,
            lastMonth = 72,
            lastYear = 66,
            weeklyTrend = listOf(64, 66, 67, 69, 71, 73, 74)
        ),
        SkillTrend(
            label = "Strength",
            currentScore = 81,
            lastMonth = 79,
            lastYear = 73,
            weeklyTrend = listOf(72, 74, 75, 77, 78, 80, 81)
        )
    )

    val riskSignals = listOf(
        RiskSignal(
            titleRes = R.string.risk_overtraining_title,
            severity = MetricStatus.Warning,
            descriptionRes = R.string.risk_overtraining_desc,
            impactRes = R.string.risk_overtraining_impact
        ),
        RiskSignal(
            titleRes = R.string.risk_injury_title,
            severity = MetricStatus.Critical,
            descriptionRes = R.string.risk_injury_desc,
            impactRes = R.string.risk_injury_impact
        ),
        RiskSignal(
            titleRes = R.string.risk_sleep_title,
            severity = MetricStatus.Warning,
            descriptionRes = R.string.risk_sleep_desc,
            impactRes = R.string.risk_sleep_impact
        ),
        RiskSignal(
            titleRes = R.string.risk_hydration_title,
            severity = MetricStatus.Warning,
            descriptionRes = R.string.risk_hydration_desc,
            impactRes = R.string.risk_hydration_impact
        )
    )

    val alerts = listOf(
        AlertItem(R.string.alert_fatigue_title, R.string.alert_fatigue_detail, MetricStatus.Warning),
        AlertItem(R.string.alert_hydration_title, R.string.alert_hydration_detail, MetricStatus.Warning),
        AlertItem(R.string.alert_hamstring_title, R.string.alert_hamstring_detail, MetricStatus.Critical)
    )

    val aiInsights = listOf(
        AiInsight(
            titleRes = R.string.insight_training_title,
            summaryRes = R.string.insight_training_summary,
            actionRes = R.string.insight_training_action,
            category = InsightCategory.Training,
            confidence = 84
        ),
        AiInsight(
            titleRes = R.string.insight_recovery_title,
            summaryRes = R.string.insight_recovery_summary,
            actionRes = R.string.insight_recovery_action,
            category = InsightCategory.Recovery,
            confidence = 78
        ),
        AiInsight(
            titleRes = R.string.insight_nutrition_title,
            summaryRes = R.string.insight_nutrition_summary,
            actionRes = R.string.insight_nutrition_action,
            category = InsightCategory.Nutrition,
            confidence = 72
        ),
        AiInsight(
            titleRes = R.string.insight_injury_title,
            summaryRes = R.string.insight_injury_summary,
            actionRes = R.string.insight_injury_action,
            category = InsightCategory.InjuryRisk,
            confidence = 81
        ),
        AiInsight(
            titleRes = R.string.insight_sleep_title,
            summaryRes = R.string.insight_sleep_summary,
            actionRes = R.string.insight_sleep_action,
            category = InsightCategory.Sleep,
            confidence = 76
        )
    )

    val lifestyleProfile = LifestyleProfile(
        avgSleepHours = 7.1,
        hydrationLiters = 2.3,
        caffeineMg = 180,
        nutritionStyle = NutritionStyle.AthletePerformance,
        trainingSessionsPerWeek = 5,
        stressLevel = StressLevel.Moderate,
        travelDaysPerMonth = 3,
        recentInjuryNotes = "Hamstring tightness (monitoring)"
    )

    val athleteProfiles = listOf(
        AthleteProfileCard(
            id = "athlete-001",
            name = "Sami Al Harbi",
            sport = "Football - Riyadh Academy",
            role = UserRole.Athlete,
            lifestyle = lifestyleProfile
        ),
        AthleteProfileCard(
            id = "athlete-002",
            name = "Noura Al Otaibi",
            sport = "Track - Sprint (Jeddah)",
            role = UserRole.Athlete,
            lifestyle = LifestyleProfile(
                avgSleepHours = 7.8,
                hydrationLiters = 2.7,
                caffeineMg = 90,
                nutritionStyle = NutritionStyle.Balanced,
                trainingSessionsPerWeek = 6,
                stressLevel = StressLevel.Low,
                travelDaysPerMonth = 1,
                recentInjuryNotes = "No active injuries"
            )
        ),
        AthleteProfileCard(
            id = "athlete-003",
            name = "Abdullah Al Zahrani",
            sport = "Basketball - Eastern Region",
            role = UserRole.Athlete,
            lifestyle = LifestyleProfile(
                avgSleepHours = 6.6,
                hydrationLiters = 2.0,
                caffeineMg = 240,
                nutritionStyle = NutritionStyle.HighProtein,
                trainingSessionsPerWeek = 4,
                stressLevel = StressLevel.High,
                travelDaysPerMonth = 4,
                recentInjuryNotes = "Lower back tightness after travel"
            )
        )
    )

    val twinZones = listOf(
        TwinZone(
            label = "Hamstrings",
            group = MuscleGroup.Hamstrings,
            offsetXRatio = -0.14f,
            offsetYRatio = 0.58f,
            radiusRatio = 0.12f,
            color = Color(0x66F07A7A)
        ),
        TwinZone(
            label = "Lower back",
            group = MuscleGroup.LowerBack,
            offsetXRatio = 0.0f,
            offsetYRatio = 0.44f,
            radiusRatio = 0.10f,
            color = Color(0x66F5B56B)
        ),
        TwinZone(
            label = "Shoulders",
            group = MuscleGroup.Shoulders,
            offsetXRatio = 0.16f,
            offsetYRatio = 0.30f,
            radiusRatio = 0.09f,
            color = Color(0x6688D6FF)
        ),
        TwinZone(
            label = "Quads",
            group = MuscleGroup.Quads,
            offsetXRatio = 0.12f,
            offsetYRatio = 0.68f,
            radiusRatio = 0.10f,
            color = Color(0x6684D97A)
        ),
        TwinZone(
            label = "Calves",
            group = MuscleGroup.Calves,
            offsetXRatio = -0.10f,
            offsetYRatio = 0.82f,
            radiusRatio = 0.08f,
            color = Color(0x66A986FF)
        )
    )

    val twinForecast = listOf(
        TwinForecastDay("Day 1", readiness = 84, riskLabel = "Low risk", riskColor = Color(0xFF1AAE6F), focus = "Speed + mobility"),
        TwinForecastDay("Day 2", readiness = 79, riskLabel = "Moderate", riskColor = Color(0xFFF0A13E), focus = "Strength + stability"),
        TwinForecastDay("Day 3", readiness = 86, riskLabel = "Low risk", riskColor = Color(0xFF1AAE6F), focus = "Tactical load"),
        TwinForecastDay("Day 4", readiness = 74, riskLabel = "Moderate", riskColor = Color(0xFFF0A13E), focus = "Recovery focus"),
        TwinForecastDay("Day 5", readiness = 81, riskLabel = "Low risk", riskColor = Color(0xFF1AAE6F), focus = "Acceleration")
    )

    val talentIndicators = listOf(
        TalentIndicator(R.string.talent_indicator_speed, 92, R.string.talent_indicator_speed_note),
        TalentIndicator(R.string.talent_indicator_cod, 88, R.string.talent_indicator_cod_note),
        TalentIndicator(R.string.talent_indicator_tactical, 90, R.string.talent_indicator_tactical_note),
        TalentIndicator(R.string.talent_indicator_recovery, 84, R.string.talent_indicator_recovery_note),
        TalentIndicator(R.string.talent_indicator_injury, 76, R.string.talent_indicator_injury_note)
    )
}

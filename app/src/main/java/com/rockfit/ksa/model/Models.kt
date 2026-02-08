package com.rockfit.ksa.model

enum class MetricStatus {
    Stable,
    Warning,
    Critical
}

enum class UserRole {
    Athlete,
    Coach,
    Medical,
    Federation
}

enum class ReturnToPlayStatus {
    Cleared,
    Restricted,
    NotCleared
}

enum class InsightCategory {
    Training,
    Recovery,
    Nutrition,
    InjuryRisk,
    Sleep
}

enum class NutritionStyle {
    Balanced,
    HighProtein,
    LowCarb,
    Mediterranean,
    AthletePerformance
}

enum class StressLevel {
    Low,
    Moderate,
    High
}

enum class MuscleGroup {
    Hamstrings,
    LowerBack,
    Shoulders,
    Quads,
    Calves
}

data class HealthMetric(
    val title: String,
    val value: String,
    val unit: String,
    val status: MetricStatus
)

data class HealthReading(
    val label: String,
    val value: String,
    val timestamp: String
)

data class SkillScore(
    val label: String,
    val score: Int
)

data class SkillTrend(
    val label: String,
    val currentScore: Int,
    val lastMonth: Int,
    val lastYear: Int,
    val weeklyTrend: List<Int>
)

data class RiskSignal(
    val titleRes: Int,
    val severity: MetricStatus,
    val descriptionRes: Int,
    val impactRes: Int
)

data class AlertItem(
    val titleRes: Int,
    val detailRes: Int,
    val severity: MetricStatus
)

data class AthleteSnapshot(
    val name: String,
    val sport: String,
    val recoveryScore: Int,
    val readiness: String,
    val injuryRisk: String
)

data class AthleteProfile(
    val fullName: String,
    val birthYear: Int,
    val heightCm: Int,
    val weightKg: Int,
    val bmi: Double,
    val bodyFatPercent: Double,
    val muscleMassKg: Double,
    val hydrationLiters: Double,
    val sleepScore: Int
)

data class AthleteProfileCard(
    val id: String,
    val name: String,
    val sport: String,
    val role: UserRole,
    val lifestyle: LifestyleProfile
)

data class InjuryLog(
    val title: String,
    val date: String,
    val status: String,
    val expectedReturn: String
)

data class TrainingSession(
    val label: String,
    val load: Int,
    val durationMinutes: Int,
    val distanceKm: Double
)

data class MedicalReport(
    val summary: String,
    val prescription: String,
    val doctor: String,
    val date: String,
    val returnToPlay: ReturnToPlayStatus
)

data class AiInsight(
    val titleRes: Int,
    val summaryRes: Int,
    val actionRes: Int,
    val category: InsightCategory,
    val confidence: Int
)

data class LifestyleProfile(
    val avgSleepHours: Double,
    val hydrationLiters: Double,
    val caffeineMg: Int,
    val nutritionStyle: NutritionStyle,
    val trainingSessionsPerWeek: Int,
    val stressLevel: StressLevel,
    val travelDaysPerMonth: Int,
    val recentInjuryNotes: String
)

data class PredictiveInput(
    val loadIndex: Int,
    val sleepHours: Double,
    val hydrationLiters: Double,
    val readinessScore: Int,
    val stressLevel: StressLevel,
    val recentInjuryNotes: String
)

data class RiskScore(
    val label: String,
    val score: Int,
    val status: MetricStatus,
    val explanation: String
)

data class TalentIndicator(
    val labelRes: Int,
    val percentile: Int,
    val noteRes: Int
)

data class TwinZone(
    val label: String,
    val group: MuscleGroup,
    val offsetXRatio: Float,
    val offsetYRatio: Float,
    val radiusRatio: Float,
    val color: androidx.compose.ui.graphics.Color
)

data class TwinForecastDay(
    val label: String,
    val readiness: Int,
    val riskLabel: String,
    val riskColor: androidx.compose.ui.graphics.Color,
    val focus: String
)

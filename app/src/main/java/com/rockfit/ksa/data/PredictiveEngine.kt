package com.rockfit.ksa.data

import com.rockfit.ksa.model.MetricStatus
import com.rockfit.ksa.model.PredictiveInput
import com.rockfit.ksa.model.RiskScore
import com.rockfit.ksa.model.StressLevel

object PredictiveEngine {
    fun buildScores(input: PredictiveInput): List<RiskScore> {
        val loadRisk = riskScore(
            label = "Training load risk",
            base = input.loadIndex,
            sleepHours = input.sleepHours,
            hydration = input.hydrationLiters,
            stress = input.stressLevel
        )
        val injuryRisk = injuryScore(input)
        val fatigueRisk = fatigueScore(input)
        return listOf(loadRisk, injuryRisk, fatigueRisk)
    }

    private fun riskScore(
        label: String,
        base: Int,
        sleepHours: Double,
        hydration: Double,
        stress: StressLevel
    ): RiskScore {
        val sleepPenalty = if (sleepHours < 7.0) 8 else 0
        val hydrationPenalty = if (hydration < 2.5) 6 else 0
        val stressPenalty = when (stress) {
            StressLevel.High -> 10
            StressLevel.Moderate -> 6
            StressLevel.Low -> 0
        }
        val score = (base + sleepPenalty + hydrationPenalty + stressPenalty).coerceIn(10, 95)
        val status = statusFromScore(score)
        return RiskScore(
            label = label,
            score = score,
            status = status,
            explanation = "Sleep ${sleepHours}h, hydration ${hydration}L, stress ${stress.name.lowercase()}."
        )
    }

    private fun injuryScore(input: PredictiveInput): RiskScore {
        val base = 42
        val injuryPenalty = if (input.recentInjuryNotes.contains("hamstring", true)) 18 else 0
        val readinessPenalty = if (input.readinessScore < 75) 8 else 0
        val score = (base + injuryPenalty + readinessPenalty).coerceIn(10, 95)
        return RiskScore(
            label = "Injury probability",
            score = score,
            status = statusFromScore(score),
            explanation = "Readiness ${input.readinessScore}%, notes: ${input.recentInjuryNotes}."
        )
    }

    private fun fatigueScore(input: PredictiveInput): RiskScore {
        val base = input.loadIndex / 2
        val sleepPenalty = if (input.sleepHours < 7.0) 10 else 0
        val score = (base + sleepPenalty).coerceIn(10, 95)
        return RiskScore(
            label = "Fatigue index",
            score = score,
            status = statusFromScore(score),
            explanation = "Load ${input.loadIndex}, sleep ${input.sleepHours}h."
        )
    }

    private fun statusFromScore(score: Int): MetricStatus {
        return when {
            score >= 75 -> MetricStatus.Critical
            score >= 55 -> MetricStatus.Warning
            else -> MetricStatus.Stable
        }
    }
}

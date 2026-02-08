package com.rockfit.ksa.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavHostController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rockfit.ksa.data.AthleteStore
import com.rockfit.ksa.data.SessionStore
import com.rockfit.ksa.R
import com.rockfit.ksa.model.AthleteProfileCard
import com.rockfit.ksa.model.UserRole
import com.rockfit.ksa.ui.screens.alerts.AlertsScreen
import com.rockfit.ksa.ui.screens.auth.LoginScreen
import com.rockfit.ksa.ui.screens.dashboard.DashboardScreen
import com.rockfit.ksa.ui.screens.health.HealthScreen
import com.rockfit.ksa.ui.screens.performance.PerformanceScreen
import com.rockfit.ksa.ui.screens.records.RecordsScreen
import com.rockfit.ksa.ui.screens.settings.SettingsScreen
import com.rockfit.ksa.ui.screens.skills.SkillsScreen
import com.rockfit.ksa.ui.screens.talent.TalentEngineScreen
import com.rockfit.ksa.ui.screens.vr.VrTrainingScreen
import com.rockfit.ksa.ui.theme.RockfitBackground
import com.rockfit.ksa.ui.theme.RockfitTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class UserSession(
    val email: String,
    val role: UserRole
)

@Composable
fun RockfitApp() {
    val configuration = LocalConfiguration.current
    val locale = configuration.locales[0]
    val isRtl = locale.language == "ar"
    androidx.compose.runtime.CompositionLocalProvider(
        LocalLayoutDirection provides if (isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr
    ) {
        RockfitTheme {
            var session by remember { mutableStateOf<UserSession?>(null) }
            val context = LocalContext.current
            val scope = rememberCoroutineScope()
            var athletes by remember { mutableStateOf<List<AthleteProfileCard>>(AthleteStore.athletes) }
            var activeAthlete by remember { mutableStateOf<AthleteProfileCard?>(AthleteStore.athletes.firstOrNull()) }

        LaunchedEffect(Unit) {
            AthleteStore.ensureSeed(context)
            AthleteStore.athletesFlow(context).collectLatest { list ->
                athletes = list
            }
            SessionStore.sessionFlow(context).collectLatest { stored ->
                if (stored != null && session == null) {
                    session = UserSession(stored.email, UserRole.valueOf(stored.role))
                }
            }
            AthleteStore.activeAthleteIdFlow(context).collectLatest { athleteId ->
                activeAthlete = athletes.firstOrNull { it.id == athleteId }
                    ?: athletes.firstOrNull()
            }
        }

            RockfitBackground {
                if (session == null) {
                    LoginScreen(onSignInSuccess = { email, role ->
                        session = UserSession(email, role)
                        scope.launch {
                            SessionStore.setSession(context, email, role.name)
                        }
                    })
                } else {
                    RockfitShell(
                        session = session,
                        activeAthlete = activeAthlete,
                        athletes = athletes,
                        onSelectAthlete = { athleteId ->
                            scope.launch {
                                AthleteStore.setActiveAthlete(context, athleteId)
                            }
                        },
                        onSignOut = {
                            scope.launch { SessionStore.clear(context) }
                            session = null
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RockfitShell(
    session: UserSession?,
    activeAthlete: AthleteProfileCard?,
    athletes: List<AthleteProfileCard>,
    onSelectAthlete: (String) -> Unit,
    onSignOut: () -> Unit
) {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route ?: RockfitDestination.Dashboard.route
    val bottomTabs = remember {
        listOf(
            RockfitDestination.Dashboard,
            RockfitDestination.Health,
            RockfitDestination.Skills,
            RockfitDestination.Performance,
            RockfitDestination.Alerts
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                ),
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                actions = {
                    IconButton(onClick = { navController.navigate(RockfitDestination.Settings.route) }) {
                        Icon(
                            imageVector = RockfitIcons.Settings,
                            contentDescription = stringResource(id = R.string.settings)
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                bottomTabs.forEach { tab ->
                    val selected = currentRoute == tab.route
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            navController.navigate(tab.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(imageVector = tab.icon, contentDescription = stringResource(id = tab.labelRes)) },
                        label = { Text(stringResource(id = tab.labelRes)) }
                    )
                }
            }
        }
    ) { innerPadding ->
        RockfitNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            session = session,
            activeAthlete = activeAthlete,
            athletes = athletes,
            onSelectAthlete = onSelectAthlete,
            onSignOut = onSignOut
        )
    }
}

@Composable
private fun RockfitNavHost(
    navController: NavHostController,
    modifier: Modifier,
    session: UserSession?,
    activeAthlete: AthleteProfileCard?,
    athletes: List<AthleteProfileCard>,
    onSelectAthlete: (String) -> Unit,
    onSignOut: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = RockfitDestination.Dashboard.route,
        modifier = modifier
    ) {
        composable(RockfitDestination.Dashboard.route) { DashboardScreen(activeAthlete = activeAthlete) }
        composable(RockfitDestination.Health.route) { HealthScreen() }
        composable(RockfitDestination.Skills.route) { SkillsScreen() }
        composable(RockfitDestination.Performance.route) { PerformanceScreen(activeAthlete = activeAthlete) }
        composable(RockfitDestination.Alerts.route) { AlertsScreen() }
        composable(RockfitDestination.Records.route) { RecordsScreen() }
        composable(RockfitDestination.VRTraining.route) { VrTrainingScreen() }
        composable(RockfitDestination.TalentEngine.route) { TalentEngineScreen() }
        composable(RockfitDestination.Settings.route) {
            SettingsScreen(
                session = session,
                athletes = athletes,
                activeAthlete = activeAthlete,
                onSelectAthlete = { athlete -> onSelectAthlete(athlete.id) },
                onOpenVr = {
                    navController.navigate(RockfitDestination.VRTraining.route)
                },
                        onOpenTalent = {
                    navController.navigate(RockfitDestination.TalentEngine.route)
                },
                onSave = {
                    navController.navigate(RockfitDestination.Dashboard.route) {
                        popUpTo(RockfitDestination.Settings.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onSignOut = onSignOut
            )
        }
    }
}

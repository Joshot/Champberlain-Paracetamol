package com.example.paracetamol.screen

sealed class Screen(val route: String) {
//    object LandingScreen : Screen("landing_screen")
    object LoginScreen : Screen("login_screen")
    object RegisterScreen : Screen("register_screen")
    object HomeScreen : Screen("home_screen")
    object SearchScreen : Screen("search_screen")
    object ProfileScreen : Screen("profile_screen")
    object AdminMemberListScreen : Screen("admin_member_list")
    object AdminMemberDetailScreen : Screen("admin_member_detail_screen")
    object AdminViewMemberScreen : Screen("admin_view_member_screen")
    object CreateScreen : Screen("create_screen")
    object JoinScreen : Screen("join_screen")
    object ArchiveScreen : Screen("archive_screen")
    object UserGroupScreen : Screen("user_group_screen")
    object MemberGroupScreen : Screen("member_group_screen")
    object AdminProfileUserScreen : Screen("admin_profile_user_screen")
    object PayScreen : Screen("pay_screen")
    object AdminPaidScreen : Screen("admin_paid_screen")
    object AdminNewDendaScreen : Screen("admin_new_denda_screen")
    object AdminUpdateDendaScreen : Screen("admin_update_denda_screen")


}
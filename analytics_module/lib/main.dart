import 'package:flutter/material.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      // 1. Remove the "Debug" banner
      debugShowCheckedModeBanner: false,

      // 2. Match the Kotlin "TodoTheme"
      theme: ThemeData(
        useMaterial3: true,
        brightness: Brightness.dark, // Forces Dark Mode
        colorScheme: ColorScheme.dark(
          // Kotlin: primary = Color(0xFF1EB980)
          primary: const Color(0xFF1EB980),

          // Kotlin: secondary = Color(0xFF03DAC6)
          secondary: const Color(0xFF03DAC6),

          // Kotlin: tertiary = Color(0xFF045D56)
          tertiary: const Color(0xFF045D56),

          // Standard dark background for Material 3
          background: const Color(0xFF121212),
          surface: const Color(0xFF1E1E1E),
        ),
        appBarTheme: const AppBarTheme(
          centerTitle: true,
          backgroundColor: Color(0xFF1E1E1E), // Dark surface color
          elevation: 0,
        ),
      ),

      // Routing logic (kept the same)
      initialRoute: '/analytics',
      onGenerateRoute: (settings) {
        final uri = Uri.parse(settings.name ?? '');
        final completed = int.tryParse(uri.queryParameters['completed'] ?? '0') ?? 0;
        final pending = int.tryParse(uri.queryParameters['pending'] ?? '0') ?? 0;

        if (uri.path == '/analytics') {
          return MaterialPageRoute(
              builder: (_) => AnalyticsScreen(completed: completed, pending: pending));
        }
        return null;
      },
    );
  }
}

class AnalyticsScreen extends StatelessWidget {
  final int completed;
  final int pending;

  const AnalyticsScreen({required this.completed, required this.pending, super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("Analytics")),
      body: Padding(
        padding: const EdgeInsets.all(24),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // Using Cards to make it look more like a native dashboard
            _buildStatCard(context, "Completed Tasks", completed, Icons.check_circle_outline),
            const SizedBox(height: 16),
            _buildStatCard(context, "Pending Tasks", pending, Icons.pending_actions),
          ],
        ),
      ),
    );
  }

  Widget _buildStatCard(BuildContext context, String label, int count, IconData icon) {
    final colorScheme = Theme.of(context).colorScheme;

    return Card(
      elevation: 2,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
      color: colorScheme.surface, // Matches the dark surface look
      child: Padding(
        padding: const EdgeInsets.all(20),
        child: Row(
          children: [
            Icon(icon, size: 40, color: colorScheme.primary),
            const SizedBox(width: 20),
            Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  label,
                  style: Theme.of(context).textTheme.bodyLarge?.copyWith(
                    color: Colors.white70,
                  ),
                ),
                Text(
                  "$count",
                  style: Theme.of(context).textTheme.headlineMedium?.copyWith(
                    fontWeight: FontWeight.bold,
                    color: Colors.white,
                  ),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}
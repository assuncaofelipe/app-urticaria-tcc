import SwiftUI

// MARK: - Model

struct MenuItemData: Identifiable {
    let id = UUID()
    let title: String
    let description: String
    let systemIcon: String
    let containerColor: Color
    let iconColor: Color
}

private let menuItems: [MenuItemData] = [
    MenuItemData(
        title: String(localized: "menu_questionnaires_title"),
        description: String(localized: "menu_questionnaires_desc"),
        systemIcon: "checklist",
        containerColor: Color(hex: "EDE7F6"),
        iconColor: Color(hex: "5E35B1")
    ),
    MenuItemData(
        title: String(localized: "menu_register_lesion_title"),
        description: String(localized: "menu_register_lesion_desc"),
        systemIcon: "camera.fill",
        containerColor: Color(hex: "FCE4EC"),
        iconColor: Color(hex: "C62828")
    ),
    MenuItemData(
        title: String(localized: "menu_what_is_urticaria_title"),
        description: String(localized: "menu_what_is_urticaria_desc"),
        systemIcon: "book.fill",
        containerColor: Color(hex: "E3F2FD"),
        iconColor: Color(hex: "1565C0")
    ),
    MenuItemData(
        title: String(localized: "menu_contact_title"),
        description: String(localized: "menu_contact_desc"),
        systemIcon: "phone.fill",
        containerColor: Color(hex: "E8F5E9"),
        iconColor: Color(hex: "2E7D32")
    ),
]

// MARK: - HomeView

struct HomeView: View {
    @State private var isGridView = false

    private let columns = [GridItem(.flexible()), GridItem(.flexible())]

    var body: some View {
        NavigationStack {
            Group {
                if isGridView {
                    gridContent
                } else {
                    listContent
                }
            }
            .background(Color(hex: "F5F7FF").ignoresSafeArea())
            .navigationTitle(String(localized: "home_title"))
            .navigationBarTitleDisplayMode(.large)
            .toolbar {
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button {
                        withAnimation(.easeInOut(duration: 0.25)) {
                            isGridView.toggle()
                        }
                    } label: {
                        Image(systemName: isGridView ? "list.bullet" : "square.grid.2x2")
                            .foregroundColor(Color(hex: "5B7FBE"))
                    }
                    .accessibilityLabel(
                        isGridView
                            ? String(localized: "action_view_list")
                            : String(localized: "action_view_grid")
                    )
                }
            }
        }
    }

    // MARK: List

    private var listContent: some View {
        ScrollView {
            LazyVStack(spacing: 12) {
                ForEach(menuItems) { item in
                    MenuListCard(item: item)
                }
            }
            .padding()
        }
    }

    // MARK: Grid

    private var gridContent: some View {
        ScrollView {
            LazyVGrid(columns: columns, spacing: 12) {
                ForEach(menuItems) { item in
                    MenuGridCard(item: item)
                }
            }
            .padding()
        }
    }
}

// MARK: - List Card

struct MenuListCard: View {
    let item: MenuItemData

    var body: some View {
        HStack(spacing: 16) {
            ZStack {
                Circle()
                    .fill(item.containerColor)
                    .frame(width: 52, height: 52)
                Image(systemName: item.systemIcon)
                    .foregroundColor(item.iconColor)
                    .font(.system(size: 22, weight: .medium))
            }
            VStack(alignment: .leading, spacing: 2) {
                Text(item.title)
                    .font(.system(size: 16, weight: .semibold))
                    .foregroundColor(.primary)
                Text(item.description)
                    .font(.system(size: 13))
                    .foregroundColor(.secondary)
            }
            Spacer()
            Image(systemName: "chevron.right")
                .foregroundColor(.secondary)
                .font(.system(size: 14, weight: .medium))
        }
        .padding(16)
        .background(Color.white)
        .clipShape(RoundedRectangle(cornerRadius: 16))
        .shadow(color: .black.opacity(0.06), radius: 8, x: 0, y: 2)
    }
}

// MARK: - Grid Card

struct MenuGridCard: View {
    let item: MenuItemData

    var body: some View {
        VStack(spacing: 12) {
            ZStack {
                RoundedRectangle(cornerRadius: 16)
                    .fill(item.containerColor)
                    .frame(width: 60, height: 60)
                Image(systemName: item.systemIcon)
                    .foregroundColor(item.iconColor)
                    .font(.system(size: 26, weight: .medium))
            }
            Text(item.title)
                .font(.system(size: 14, weight: .semibold))
                .multilineTextAlignment(.center)
                .foregroundColor(.primary)
            Text(item.description)
                .font(.system(size: 12))
                .multilineTextAlignment(.center)
                .foregroundColor(.secondary)
                .lineLimit(2)
        }
        .padding(16)
        .frame(maxWidth: .infinity)
        .aspectRatio(1, contentMode: .fit)
        .background(Color.white)
        .clipShape(RoundedRectangle(cornerRadius: 20))
        .shadow(color: .black.opacity(0.06), radius: 8, x: 0, y: 2)
    }
}

// MARK: - Color hex helper

extension Color {
    init(hex: String) {
        var value: UInt64 = 0
        Scanner(string: hex).scanHexInt64(&value)
        self.init(
            red: Double((value >> 16) & 0xFF) / 255,
            green: Double((value >> 8) & 0xFF) / 255,
            blue: Double(value & 0xFF) / 255
        )
    }
}

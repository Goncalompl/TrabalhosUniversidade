using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace Clientes.Data.Migrations
{
    public partial class CampanhaOnline : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<bool>(
                name: "Online",
                table: "Campanha",
                type: "bit",
                nullable: false,
                defaultValue: false);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "Online",
                table: "Campanha");
        }
    }
}

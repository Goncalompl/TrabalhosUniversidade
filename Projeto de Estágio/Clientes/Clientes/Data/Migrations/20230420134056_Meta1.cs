using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace Clientes.Data.Migrations
{
    public partial class Meta1 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "Files1");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Files1",
                columns: table => new
                {
                    FileId = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    File1ClassFileId = table.Column<int>(type: "int", nullable: true),
                    Name = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Path = table.Column<string>(type: "nvarchar(max)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Files1", x => x.FileId);
                    table.ForeignKey(
                        name: "FK_Files1_Files1_File1ClassFileId",
                        column: x => x.File1ClassFileId,
                        principalTable: "Files1",
                        principalColumn: "FileId");
                });

            migrationBuilder.CreateIndex(
                name: "IX_Files1_File1ClassFileId",
                table: "Files1",
                column: "File1ClassFileId");
        }
    }
}

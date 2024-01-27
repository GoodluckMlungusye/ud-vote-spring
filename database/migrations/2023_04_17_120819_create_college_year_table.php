<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('college_years', function (Blueprint $table) {
            // Composite primary for college_id and year
            $table->id();
            $table->foreignId('college_id')->constrained('colleges')->onUpdate('cascade')->onDelete('cascade');
            $table->integer('year');
            $table->integer('registered_students');
            $table->integer('voted_students')->default(0);
            $table->integer('universal_contestants');
            $table->float('rating')->default(0.0);
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::table('college_years', function (Blueprint $table) {
            //
        });
    }
};

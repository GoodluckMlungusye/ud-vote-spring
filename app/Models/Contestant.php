<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;
use Illuminate\Database\Eloquent\Relations\BelongsToMany;

class Contestant extends Model
{
    use HasFactory;

    protected $guarded = [];

    protected $with = ['voter','category'];


    public function voter(): BelongsTo
    {
        return $this->belongsTo(Voter::class,'voter_id','id');

    }


    public function category(): BelongsTo
    {
        return $this->belongsTo(Category::class,'category_id','id');
    }

    public function voters(): BelongsToMany {
        return $this->belongsToMany(Voter::class,'voter_contestants');
    }
}
